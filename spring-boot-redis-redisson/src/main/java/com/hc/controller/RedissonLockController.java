package com.hc.controller;

import com.hc.service.RedissonLockDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class RedissonLockController {

    @Autowired
    private RedissonLockDemo redissonLockDemo;

    @RequestMapping("/redissonLockWatchDog")
    public String redissonLockWatchDog(){
        String key = "my-lock-watch-dog";
        return  redissonLockDemo.redissonLockWatchDog(key);
    }

    @RequestMapping("/redissonLock")
    public String redissonLock(Integer sleepTime){
        String key = "my-lock";
        return  redissonLockDemo.redissonLock(key,sleepTime);
    }
}
