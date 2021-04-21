package com.example.test.Controller;

import com.example.test.Entity.*;
import com.example.test.Service.LoanService;
import com.example.test.Severce.JDBCUtil;
import com.example.test.mapper.LoanMapper;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.sql.*;
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
    public ResponseEntity<?> get(@RequestBody LoginRequest loginRequest){
        String username=loginRequest.getUsername();
        String password=loginRequest.getPassword();
        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //封装参数，不要替换为Map与HashMap，否则参数无法传递
        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        //添加请求的参数
        params.add("username", username);  //必传
        params.add("password", password);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params,headers);
        ResponseEntity<String>responseEntity=this.restTemplate.exchange(
                Config.BANK_LOGIN_URL,HttpMethod.POST,entity,String.class
        );
        if (responseEntity.getStatusCode()==HttpStatus.OK){
            return responseEntity;
        }
        return null;
    }


    //实现返回账号等级*******
    @RequestMapping("/getAccountGrade")
    public ResponseEntity<?> getAccountGrade(@RequestBody JSONObject jsonObject){
        HashMap<String, Object> result = new HashMap<>();
        //待完善......
        String accountNum = jsonObject.getString("account").toString();

        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "select * from accountinfo where accountNum = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,accountNum);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int deposit = resultSet.getInt(3);
            int loan = resultSet.getInt(2);
            if((deposit-loan) > 500000){
                result.put("grade",1);
            }else if((deposit-loan) >= 0){
                result.put("grade",2);
            }else {
                result.put("grade",3);
            }
            resultSet.close();
            preparedStatement.close();
            JDBCUtil.releaseConnection(connection);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(result.get("grade"));

        return ResponseEntity.ok(result);
    }


    //实现产品价格返回----OK
    @RequestMapping("/getProductPrice/{productId}")
    public ResponseEntity<?> getProductPrice(@PathVariable int productId){
        //Coding here...
        HashMap<String, Object> result = new HashMap<>();

        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "select price from products where type = "+productId;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int price = resultSet.getInt(1);
            result.put("productPrice",price);

            resultSet.close();
            preparedStatement.close();
            JDBCUtil.releaseConnection(connection);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(result);
    }

    //实现账号的罚金返回----OK
    @RequestMapping("/canBuyProduct")
    public ResponseEntity<?> canBuyProduct(@RequestBody JSONObject jsonObject){
        //Coding here...
        String accountNum = jsonObject.getString("account").toString();

        int fine = 0;
        HashMap<String, Object> result = new HashMap<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "select * from accountinfo where accountNum = " + accountNum;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            fine = resultSet.getInt(4);
            result.put("fine",fine);

            resultSet.close();
            preparedStatement.close();
            JDBCUtil.releaseConnection(connection);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if(fine == 0){
            result.put("flag",false);
        }else {
            result.put("flag",true);
        }

        return ResponseEntity.ok(result);
    }

    //得到账户余额----OK
    @RequestMapping("/getAccountBalance")
    public ResponseEntity<?> getAccountBalance(@RequestBody JSONObject jsonObject){
        //Coding here...
        String accountNum = jsonObject.getString("account").toString();
        int balance = 0;

        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "select deposit from accountinfo where accountNum = "+ accountNum;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            balance = resultSet.getInt(1);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        HashMap<String, Object> result = new HashMap<>();
        result.put("accountBalance",balance);
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
     * */
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
    public ResponseEntity<?> buyProduct(@RequestBody JSONObject jsonObject){
        for(Object str:jsonObject.keySet()){
            System.out.println(str + ":" +jsonObject.get(str));
            System.out.println("遍历");
        }


        String account = jsonObject.getString("account").toString();
        int productID = Integer.parseInt(jsonObject.getString("productId"));
        int fine = Integer.parseInt(jsonObject.getString("fine"));
        Date date = strToDate(jsonObject.getString("date"));
        int duration = Integer.parseInt(jsonObject.getString("time"));
        int productNum = Integer.parseInt(jsonObject.getString("productNum"));



//        String account = "001";
//        int productID = 2;
//        int fine = 0;
//        Date date = new Date(new java.util.Date().getTime());
//        int duration = 3;
//        int productNum = 4;

        System.out.println("pointer1");

        int balance = 0;
        int price = 0;

        System.out.println("pointer2");
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "select deposit from accountinfo where accountNum = "+ account;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            balance = resultSet.getInt(1);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("pointer3");
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "select price from products where type = "+productID;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            price = resultSet.getInt(1);

            resultSet.close();
            preparedStatement.close();
            JDBCUtil.releaseConnection(connection);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("pointer4");
        System.out.println(balance-fine-productNum*price+"-====-");
        System.out.println("pointer4");

        try {

            Connection connection = JDBCUtil.getConnection();
            String sql = "Update accountinfo set deposit = ? Where accountNum = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,balance-fine-productNum*price);
            preparedStatement.setString(2,account);
            preparedStatement.executeUpdate();
            int customerId = loanMapper.getCustomerIdFromCustomerCode(Integer.parseInt(account));
            loanMapper.updateCard(balance-fine-productNum*price,customerId);


            if(fine != 0){
                String sql2 = "insert into transaction (account, time, operation,amount) values(?,?,?,?)";
                PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                preparedStatement2.setString(1,account+"");
                preparedStatement2.setDate(2,date);
                preparedStatement2.setString(3,"交罚金");
                preparedStatement2.setInt(4,fine);
                preparedStatement2.executeUpdate();
                List<Bill> bills = loanMapper.getBillFromCustomerCode(Integer.parseInt(account));
                for(Bill bill:bills){
                    loanService.payForFine(bill.getFine(),Integer.parseInt(account),bill.getPeriodNum(),bill.getLoan_num());
                }


                String sql4 = "Update accountinfo set fine = ? Where accountNum = ? ";
                PreparedStatement preparedStatement4 = connection.prepareStatement(sql4);
                preparedStatement4.setInt(1,0);
                preparedStatement4.setString(2,account);
                preparedStatement4.executeUpdate();
            }

            System.out.println("pointer5");

            String sql2 = "insert into transaction (account, time, operation,amount) values(?,?,?,?)";
            PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
            preparedStatement2.setString(1,account+"");
            preparedStatement2.setDate(2,date);
            preparedStatement2.setString(3,"购买产品"+productID);
            preparedStatement2.setInt(4,price*productNum);
            preparedStatement2.executeUpdate();

            System.out.println("pointer6");

            String sql3 = "insert into purchase_info (account, productType, time, amount, duration) values(?,?,?,?,?)";
            PreparedStatement preparedStatement3 = connection.prepareStatement(sql3);
            preparedStatement3.setString(1,account+"");
            preparedStatement3.setInt(2,productID);
            preparedStatement3.setDate(3,date);
            preparedStatement3.setInt(4,price*productNum);
            preparedStatement3.setInt(5,duration);
            preparedStatement3.executeUpdate();

            System.out.println("pointer7");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("hhhhhhhhhhhh");
        return ResponseEntity.ok(200);
    }


    /**
     * 检查purchase_inf表
     * @param jsonObject
     * @return
     */
    //得到账户盈亏数据
    @RequestMapping("/checkData")
    public ResponseEntity<?> checkData(@RequestBody JSONObject jsonObject){
        //Coding here...
        String account=jsonObject.get("account").toString();
        List<Record>list=new ArrayList<>();

        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "select * from purchase_info where account = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,account);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Record record = new Record();
                record.setAccount(account);
                int type = resultSet.getInt(2);
                String productType = "";
                switch (type){
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
                record.setDate(String.valueOf(resultSet.getDate(3)));
                record.setCondition((int)(Math.random()*2));
                list.add(record);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

//
//        Record record=new Record();
//        record.setAccount(account);
//        record.setTheProduct("基金000021");
//        record.setCondition(1);
//
//        Record record1=new Record();
//        record1.setAccount(account);
//        record1.setTheProduct("基金209001");
//        record1.setCondition(0);

        return ResponseEntity.ok(list);
    }

    //得到账户流水信息*****************
    @RequestMapping("/getAccountFlowInfo")
    public ResponseEntity<?> getAccountFlowInfo(@RequestBody JSONObject jsonObject) throws Exception {
        String account=jsonObject.get("account").toString();

        List<AccountFlow>list=new ArrayList<>();

        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "select * from transaction where account = "+account;
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            ResultSet rs = prepareStatement.executeQuery();

            while (rs.next()) {
                AccountFlow accountFlow=new AccountFlow();
                accountFlow.setAccount(account);
                String temp = rs.getString(2);
                String result = "";
                for (int i = 0; i < temp.length(); i++){
                    if(temp.charAt(i) == '-'){
                        result += ".";
                    }else {
                        result += temp.charAt(i);
                    }
                }
                accountFlow.setDate(result);
                accountFlow.setOperation(rs.getString(3));
                accountFlow.setAmount(rs.getInt(4));
                list.add(accountFlow);
            }

            rs.close();
            prepareStatement.close();
            JDBCUtil.releaseConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(list);
    }





    /*
    *//**
     * 调用远程服务
     * @param id
     * @return
     *//*
    @RequestMapping("/find-user-id/{id}")
    public LoginRequest findOne(@PathVariable Long id) {
        ResponseEntity<LoginRequest> resultResponseEntity = this.restTemplate.exchange(
                String.format("http://xxx.xxx.xxx.xxx/user/find-id/%s", id),
                HttpMethod.GET, null, LoginRequest.class);
        if (resultResponseEntity.getStatusCode() == HttpStatus.OK) {
            return resultResponseEntity.getBody();
        }
        return null;
    }


    *//**
     * 调用远程服务添加用户信息
     *
     * @param userEntity 用户实体
     * @return
     *//*
    @RequestMapping("/add-user")
    public AccountFlow addUser(@RequestBody LoginRequest userEntity) {
        HttpEntity<LoginRequest> entity = new HttpEntity<>(userEntity);
        ResponseEntity<AccountFlow> resultResponseEntity = this.restTemplate.exchange(
                "http://xxx.xxx.xxx.xxx/user/add",
                HttpMethod.POST, entity, AccountFlow.class);
        if (resultResponseEntity.getStatusCode() == HttpStatus.OK) {
            return resultResponseEntity.getBody();
        }
        return null;
    }*/
}
