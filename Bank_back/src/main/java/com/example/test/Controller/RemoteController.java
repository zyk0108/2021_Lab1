package com.example.test.Controller;

import com.example.test.Entity.AccountFlow;
import com.example.test.Entity.Config;
import com.example.test.Entity.LoginRequest;
import com.example.test.Entity.Record;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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


    //实现返回账号等级
    @RequestMapping("/getAccountGrade")
    public ResponseEntity<?> getAccountGrade(){
        HashMap<String, Object> result = new HashMap<>();
        //待完善......

        result.put("grade",1);
        return ResponseEntity.ok(result);
    }


    //实现产品价格返回
    @RequestMapping("/getProductPrice/{productId}")
    public ResponseEntity<?> getProductPrice(@PathVariable int productId){
        //Coding here...

        System.out.println(productId);
        HashMap<String, Object> result = new HashMap<>();
        result.put("productPrice",1000);
        return ResponseEntity.ok(result);
    }

    //实现账号的罚金返回
    @RequestMapping("/canBuyProduct")
    public ResponseEntity<?> canBuyProduct(){
        //Coding here...

        HashMap<String, Object> result = new HashMap<>();
        result.put("fine",100);
        result.put("flag",true);
        return ResponseEntity.ok(result);
    }

    //得到账户金额
    @RequestMapping("/getAccountBalance")
    public ResponseEntity<?> getAccountBalance(){
        //Coding here...

        HashMap<String, Object> result = new HashMap<>();
        result.put("accountBalance",60000);
        return ResponseEntity.ok(result);
    }

    //得到账户金额
    @RequestMapping("/buyProduct")
    public ResponseEntity<?> buyProduct(){
        //Coding here...

        return ResponseEntity.ok(200);
    }


    //得到账户盈亏数据
    @RequestMapping("/checkData")
    public ResponseEntity<?> checkData(@RequestBody JSONObject jsonObject){
        //Coding here...
        String account=jsonObject.get("account").toString();
        Record record=new Record();
        record.setAccount(account);
        record.setTheProduct("基金000021");
        record.setCondition(1);
        Record record1=new Record();
        record1.setAccount(account);
        record1.setTheProduct("基金209001");
        record1.setCondition(0);
        List<Record>list=new ArrayList<>();
        list.add(record);
        list.add(record1);
        return ResponseEntity.ok(list);
    }

    //得到账户流水信息
    @RequestMapping("/getAccountFlowInfo")
    public ResponseEntity<?> getAccountFlowInfo(@RequestBody JSONObject jsonObject){
        String account=jsonObject.get("account").toString();
        //coding here

        AccountFlow accountFlow=new AccountFlow();
        accountFlow.setAccount(account);
        accountFlow.setDate("2021.04.10");
        accountFlow.setOperation("购买基金200021");
        accountFlow.setAmount(2000);
        List<AccountFlow>list=new ArrayList<>();
        list.add(accountFlow);
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
