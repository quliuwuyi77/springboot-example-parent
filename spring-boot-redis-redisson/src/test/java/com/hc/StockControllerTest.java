package com.hc;

import static org.junit.Assert.assertTrue;

import com.hc.service.RedissonLockDemo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * SpringRunner 底层使用的是 JUnit
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RedissonSpringBootApplication.class})  // 指定启动类
public class StockControllerTest {

    @Autowired
    private RedissonLockDemo redissonLockDemo;

    @Test
    public void testQueryAllStock(){
        redissonLockDemo.redissonLock("hello-lock",10);
        log.info("执行完成");
    }


}
