package com.example.test.Controller;

import com.example.test.Entity.Config;
import com.example.test.Entity.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


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
    public Result addUser(@RequestBody LoginRequest userEntity) {
        HttpEntity<LoginRequest> entity = new HttpEntity<>(userEntity);
        ResponseEntity<Result> resultResponseEntity = this.restTemplate.exchange(
                "http://xxx.xxx.xxx.xxx/user/add",
                HttpMethod.POST, entity, Result.class);
        if (resultResponseEntity.getStatusCode() == HttpStatus.OK) {
            return resultResponseEntity.getBody();
        }
        return null;
    }*/
}
