package com.example.test.service;

import com.example.test.entity.*;
import com.example.test.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BuyProduct {

    @Autowired
    private LoanMapper loanMapper;

    @Autowired
    private LoanService loanService;

    public BuyProduct(LoanMapper mapper,LoanService service){
        this.loanMapper = mapper;
        this.loanService = service;
    }

    public Map<String, Object> getAccountGrade(String account) {
        Map<String, Object> result = new HashMap<>();
        AccountInfoDAOImp accountInfoDAOImp = new AccountInfoDAOImp();
        AccountInfo accountinfo = accountInfoDAOImp.getAccountInfo(account);
        int deposit = accountinfo.getDeposit(), loan = accountinfo.getLoan();
        int left = deposit - loan;
        if (left > 500000) {
            result.put("grade", 1);
        }else if (left >= 0) {
            result.put("grade", 2);
        }else {
            result.put("grade", 3);
        }
        return result;
    }
    public Map<String, Object> getProductPrice(int productId) {
        Map<String, Object> result = new HashMap<>();
        ProductsDAOImp productsDAOImp = new ProductsDAOImp();
        Products products = productsDAOImp.getProducts(productId);
        int price = products.getPrice();
        result.put("productPrice", price);
        return result;
    }


    //实现账号的罚金返回
    public Map<String, Object> canBuyProduct(String account) {
        Map<String, Object> result = new HashMap<>();
        AccountInfoDAOImp accountInfoDAOImp = new AccountInfoDAOImp();
        AccountInfo accountInfo = accountInfoDAOImp.getAccountInfo(account);
        int fine = accountInfo.getFine();

        result.put("fine", fine);
        if (fine == 0) {
            result.put("flag", false);
        } else {
            result.put("flag", true);
        }
        return result;
    }

    public Map<String, Object> getAccountBalance(String account) {
        AccountInfoDAOImp accountInfoDAOImp = new AccountInfoDAOImp();
        AccountInfo accountInfo = accountInfoDAOImp.getAccountInfo(account);

        int balance = accountInfo.getDeposit();
        Map<String, Object> result = new HashMap<>();
        result.put("accountBalance", balance);

        return result;
    }

    public static Date strToDate(String strDate) throws Exception{
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        java.util.Date d = format.parse(strDate);
        Date date = new Date(d.getTime());
        return date;
    }
    public int buyProduct(String account,int productID,int fine,String theDate,int duration,int productNum) throws Exception{
        Date date = strToDate(theDate);

        AccountInfoDAOImp accountInfoDAOImp = new AccountInfoDAOImp();
        AccountInfo accountInfo = accountInfoDAOImp.getAccountInfo(account);
        int balance = accountInfo.getDeposit();

        ProductsDAOImp productsDAOImp = new ProductsDAOImp();
        Products products = productsDAOImp.getProducts(productID);
        int price = products.getPrice();


        int depositTemp = balance - fine - productNum * price;
        accountInfoDAOImp.updateDeposit(depositTemp, account);

        int customerId = loanMapper.getCustomerIdFromCustomerCode(Integer.parseInt(account));
        loanMapper.updateCard(balance - fine - productNum * price, customerId);
        if (fine != 0) {
            accountInfoDAOImp.updateFine(account);
            List<Bill> bills = loanMapper.getBillFromCustomerCode(Integer.parseInt(account));
            for(Bill bill : bills){
                if(bill.getAlreadyFine().equals("no")){
                    System.out.println("ok");
                    loanService.payForFine(fine,Integer.parseInt(account),bill.getPeriodNum(), bill.getLoan_num());
                }
            }
        }
        int amountTemp = price * productNum;
        TransactionDAOImp transactionDAOImp = new TransactionDAOImp();
        transactionDAOImp.addTransaction(account, date, "购买产品" + productID, amountTemp);
        int Temp = price * productNum;
        PurchaseInfoDAOImp purchaseInfoDAOImp = new PurchaseInfoDAOImp();
        purchaseInfoDAOImp.addPurchaseInfo(account, productID, date, Temp, duration);

        return 200;
    }


    /**
     *得到账户盈亏数据
     * @param account
     * @return
     */
    public List<Record> checkData(String account) {
        List<Record> list = new ArrayList<>();
        PurchaseInfoDAOImp purchaseInfoDAOImp = new PurchaseInfoDAOImp();
        List<PurchaseInfo> purchaseInfos = purchaseInfoDAOImp.getInfos(account);

        for (PurchaseInfo each : purchaseInfos) {
            Record record = new Record();
            record.setAccount(account);
            int type = each.getProductType();
            String productType;
            if (type==1){
                productType = "股票";
            }else if (type==2){
                productType = "基金";
            }else {
                productType = "定期";
            }
            record.setTheProduct(productType);
            record.setDate(String.valueOf(each.getTime()));
            record.setCondition((int) (Math.random() * 2));
            list.add(record);
        }
        return list;
    }


    //得到账户流水信息
    public List<AccountFlow> getAccountFlowInfo(String account) throws Exception {
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
                }
                else {
                    result += temp.charAt(i);
                }
            }
            accountFlow.setDate(result);
            accountFlow.setOperation(transaction.getOperation());
            accountFlow.setAmount(transaction.getAmount());
            list.add(accountFlow);
        }
        return list;
    }
}
