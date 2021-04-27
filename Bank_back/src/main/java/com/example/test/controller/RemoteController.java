package com.example.test.controller;

import com.example.test.config.Config;
import com.example.test.entity.*;
import com.example.test.service.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;


//允许跨域访问
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class RemoteController {

    static BuyProduct buyProduct=new BuyProduct();

    //自动注入RestTemplate
    private final RestTemplate restTemplate;

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
        String account = jsonObject.get("account").toString();
        Map<String, Object> result = buyProduct.getAccountGrade(account);
        // System.out.println(result.get("grade"));
        return ResponseEntity.ok(result);
    }


    //实现产品价格返回----OK
    @RequestMapping("/getProductPrice/{productId}")
    public ResponseEntity<?> getProductPrice(@PathVariable int productId) {
        //Coding here...
        Map<String, Object> result = buyProduct.getProductPrice(productId);
        return ResponseEntity.ok(result);
    }

    //实现账号的罚金返回----OK
    @RequestMapping("/canBuyProduct")
    public ResponseEntity<?> canBuyProduct(@RequestBody JSONObject jsonObject) {
        //Coding here...
        String account = jsonObject.get("account").toString();
        Map<String, Object> result = buyProduct.canBuyProduct(account);
        return ResponseEntity.ok(result);
    }

    //得到账户余额----OK
    @RequestMapping("/getAccountBalance")
    public ResponseEntity<?> getAccountBalance(@RequestBody JSONObject jsonObject) {
        //Coding here...
        String account = jsonObject.get("account").toString();
        Map<String, Object> result = buyProduct.getAccountBalance(account);
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
     * @throws Exception
     */
    @RequestMapping("/buyProduct")
    public ResponseEntity<?> buyProduct(@RequestBody JSONObject jsonObject) throws Exception{
        String account = jsonObject.get("account").toString();
        int productID = Integer.parseInt(jsonObject.getString("productId"));
        int fine = Integer.parseInt(jsonObject.getString("fine"));
        String date = jsonObject.getString("date");
        int duration = Integer.parseInt(jsonObject.getString("time"));
        int productNum = Integer.parseInt(jsonObject.getString("productNum"));

        int status = buyProduct.buyProduct(account,productID,fine,date,duration,productNum);
        return ResponseEntity.ok(status);
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
        List<Record> list = buyProduct.checkData(account);
        return ResponseEntity.ok(list);
    }

    //得到账户流水信息*****************
    @RequestMapping("/getAccountFlowInfo")
    public ResponseEntity<?> getAccountFlowInfo(@RequestBody JSONObject jsonObject) throws Exception {
        String account = jsonObject.get("account").toString();
        List<AccountFlow> list = buyProduct.getAccountFlowInfo(account);
        return ResponseEntity.ok(list);
    }
}
