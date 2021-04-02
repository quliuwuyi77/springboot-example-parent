package com.hc.redis;


import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * SpringRunner 底层使用的是 JUnit
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {JedisSpringBootApplication.class})  // 指定启动类
public class JedisControllerTest {

}
