package com.example.test.Controller;

import com.example.test.Entity.Person;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Example {
    @RequestMapping("/")
    public String get(){
        return "String";
    }

    @RequestMapping("/person")
    public Person getInfo(){

        return new Person();
    }


}
