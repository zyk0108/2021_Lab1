package com.example.test.controller;

import com.example.test.config.Config;
import com.example.test.entity.*;
import com.example.test.mapper.*;
import com.example.test.service.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


//允许跨域访问
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class RemoteController {

    //自动注入RestTemplate
    private final RestTemplate restTemplate;

    @Autowired
    private LoanMapper loanMapper;

    @Autowired
    private LoanService loanService;

    @Autowired
    public RemoteController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //实现登录功能
    @RequestMapping("/login")
    public ResponseEntity<?> get(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //封装参数，不要替换为Map与HashMap，否则参数无法传递
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        //添加请求的参数
        params.add("username", username);  //必传
        params.add("password", password);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
        ResponseEntity<String> responseEntity = this.restTemplate.exchange(
                Config.BANK_LOGIN_URL, HttpMethod.POST, entity, String.class
        );
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity;
        }
        return null;
    }


    //实现返回账号等级*******
    @RequestMapping("/getAccountGrade")
    public ResponseEntity<?> getAccountGrade(@RequestBody JSONObject jsonObject) {
        HashMap<String, Object> result = new HashMap<>();
        //待完善......
        String accountNum = jsonObject.getString("account").toString();

        AccountInfoDAOImp accountInfoDAOImp = new AccountInfoDAOImp();
        AccountInfo accountinfo = accountInfoDAOImp.getAccountInfo(accountNum);

        int deposit = accountinfo.getDeposit();
        int loan = accountinfo.getLoan();

        if ((deposit - loan) > 500000) {
            result.put("grade", 1);
        } else if ((deposit - loan) >= 0) {
            result.put("grade", 2);
        } else {
            result.put("grade", 3);
        }

        System.out.println(result.get("grade"));

        return ResponseEntity.ok(result);
    }


    //实现产品价格返回----OK
    @RequestMapping("/getProductPrice/{productId}")
    public ResponseEntity<?> getProductPrice(@PathVariable int productId) {
        //Coding here...
        HashMap<String, Object> result = new HashMap<>();


        ProductsDAOImp productsDAOImp = new ProductsDAOImp();
        Products products = productsDAOImp.getProducts(productId);


        int price = products.getPrice();
        result.put("productPrice", price);

        return ResponseEntity.ok(result);
    }

    //实现账号的罚金返回----OK
    @RequestMapping("/canBuyProduct")
    public ResponseEntity<?> canBuyProduct(@RequestBody JSONObject jsonObject) {
        //Coding here...
        String accountNum = jsonObject.getString("account").toString();

        int fine = 0;
        HashMap<String, Object> result = new HashMap<>();

        AccountInfoDAOImp accountInfoDAOImp = new AccountInfoDAOImp();
        AccountInfo accountInfo = accountInfoDAOImp.getAccountInfo(accountNum);

        fine = accountInfo.getFine();

        result.put("fine", fine);


        if (fine == 0) {
            result.put("flag", false);
        } else {
            result.put("flag", true);
        }

        return ResponseEntity.ok(result);
    }

    //得到账户余额----OK
    @RequestMapping("/getAccountBalance")
    public ResponseEntity<?> getAccountBalance(@RequestBody JSONObject jsonObject) {
        //Coding here...
        String accountNum = jsonObject.getString("account").toString();
        int balance = 0;


        AccountInfoDAOImp accountInfoDAOImp = new AccountInfoDAOImp();
        AccountInfo accountInfo = accountInfoDAOImp.getAccountInfo(accountNum);

        balance = accountInfo.getDeposit();

        HashMap<String, Object> result = new HashMap<>();
        result.put("accountBalance", balance);
        return ResponseEntity.ok(result);
    }

    /**
     * 此处以确定可以购买，余额足够购买
     * 需要完成：更新用户的余额（原始余额-罚金-购买花销）
     * 添加一条还罚金的交易记录（transaction表）
     * 添加一条购买产品的交易记录（transaction表）
     * 添加一条购买记录（purchase_info表），方便后面查看盈亏
     * @param jsonObject
     * @return
     */

    /**
     * @param
     */
    public static Date strToDate(String strDate) {
        String str = strDate;
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        java.util.Date d = null;
        try {
            d = format.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Date date = new Date(d.getTime());
        return date;
    }

    @RequestMapping("/buyProduct")
    public ResponseEntity<?> buyProduct(@RequestBody JSONObject jsonObject) {
        for (Object str : jsonObject.keySet()) {
            System.out.println(str + ":" + jsonObject.get(str));
            System.out.println("遍历");
        }


        String account = jsonObject.getString("account").toString();
        int productID = Integer.parseInt(jsonObject.getString("productId"));
        int fine = Integer.parseInt(jsonObject.getString("fine"));
        Date date = strToDate(jsonObject.getString("date"));
        int duration = Integer.parseInt(jsonObject.getString("time"));
        int productNum = Integer.parseInt(jsonObject.getString("productNum"));

        System.out.println("pointer1");

        int balance = 0;
        int price = 0;


        AccountInfoDAOImp accountInfoDAOImp = new AccountInfoDAOImp();
        AccountInfo accountInfo = accountInfoDAOImp.getAccountInfo(account);
        balance = accountInfo.getDeposit();

        ProductsDAOImp productsDAOImp = new ProductsDAOImp();
        Products products = productsDAOImp.getProducts(productID);
        price = products.getPrice();


        int depositTemp = balance - fine - productNum * price;
        accountInfoDAOImp.updateDeposit(depositTemp, account);


        int customerId = loanMapper.getCustomerIdFromCustomerCode(Integer.parseInt(account));
        loanMapper.updateCard(balance - fine - productNum * price, customerId);


        if (fine != 0) {

            TransactionDAOImp transactionDAOImp = new TransactionDAOImp();
            transactionDAOImp.addTransaction(account, date, "交罚金", fine);

            List<Bill> bills = loanMapper.getBillFromCustomerCode(Integer.parseInt(account));
            for (Bill bill : bills) {
                loanService.payForFine(bill.getFine(), Integer.parseInt(account), bill.getPeriodNum(), bill.getLoan_num());
            }

            accountInfoDAOImp.updateFine(account);
        }

        int amountTemp = price * productNum;
        TransactionDAOImp transactionDAOImp = new TransactionDAOImp();
        transactionDAOImp.addTransaction(account, date, "购买产品" + productID, amountTemp);


        int Temp = price * productNum;
        PurchaseInfoDAOImp purchaseInfoDAOImp = new PurchaseInfoDAOImp();
        purchaseInfoDAOImp.addPurchaseInfo(account, productID, date, Temp, duration);

        System.out.println("pointer7");


        return ResponseEntity.ok(200);
    }


    /**
     * 检查purchase_inf表
     *
     * @param jsonObject
     * @return
     */
    //得到账户盈亏数据
    @RequestMapping("/checkData")
    public ResponseEntity<?> checkData(@RequestBody JSONObject jsonObject) {
        //Coding here...
        String account = jsonObject.get("account").toString();
        List<Record> list = new ArrayList<>();

        PurchaseInfoDAOImp purchaseInfoDAOImp = new PurchaseInfoDAOImp();
        List<PurchaseInfo> purchaseInfos = purchaseInfoDAOImp.getInfos(account);

        for (PurchaseInfo each : purchaseInfos) {

            Record record = new Record();
            record.setAccount(account);
            int type = each.getProductType();
            String productType = "";
            switch (type) {
                case 1:
                    productType = "股票";
                    break;
                case 2:
                    productType = "基金";
                    break;
                case 3:
                    productType = "定期";
                    break;
            }
            record.setTheProduct(productType);
            record.setDate(String.valueOf(each.getTime()));
            record.setCondition((int) (Math.random() * 2));
            list.add(record);
        }


        return ResponseEntity.ok(list);
    }

    //得到账户流水信息*****************
    @RequestMapping("/getAccountFlowInfo")
    public ResponseEntity<?> getAccountFlowInfo(@RequestBody JSONObject jsonObject) throws Exception {
        String account = jsonObject.get("account").toString();

        List<AccountFlow> list = new ArrayList<>();

        TransactionDAOImp transactionDAOImp = new TransactionDAOImp();
        List<Transaction> transactions = transactionDAOImp.getTransactions(account);

        for (Transaction transaction : transactions) {
            AccountFlow accountFlow = new AccountFlow();
            accountFlow.setAccount(account);
            String temp = String.valueOf(transaction.getTime());
            String result = "";
            for (int i = 0; i < temp.length(); i++) {
                if (temp.charAt(i) == '-') {
                    result += ".";
                } else {
                    result += temp.charAt(i);
                }
            }
            accountFlow.setDate(result);
            accountFlow.setOperation(transaction.getOperation());
            accountFlow.setAmount(transaction.getAmount());
            list.add(accountFlow);
        }
        return ResponseEntity.ok(list);
    }
}
