package com.hc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("hello")
@RestController
public class HelloController {

    @RequestMapping("hello")
    public String hello(String name){
        System.out.println("进行测试的hello: " + name);
        return "hello : " + name;
    }
}
