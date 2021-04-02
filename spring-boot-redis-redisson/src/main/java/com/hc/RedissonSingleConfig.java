package com.hc;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * 单机 redisson 的配置
 */
@Configuration
public class RedissonSingleConfig {

    @Bean(destroyMethod="shutdown")
    public RedissonClient singleRedissonClient() throws IOException {
        //1 创建配置
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress("redis://127.0.0.1:6379").setPassword("123456");
        //2.根据Config创建出RedissonClient
        return Redisson.create(config);
    }
}
