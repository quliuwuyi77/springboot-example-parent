package com.hc;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class RedissonSentinelConfig {

    @Bean(destroyMethod="shutdown")
    public RedissonClient sentinelRedissonClient() throws IOException {
        //1 创建配置
        Config config = new Config();
        SentinelServersConfig sentinelServersConfig = config.useSentinelServers();
        /**
         * 10.150.30.221:6394,10.150.30.222:6394,10.150.30.223:6394
         * timeout: 3600
         * password: hengchang
         */
        sentinelServersConfig.addSentinelAddress("redis://10.150.30.221:6394","redis://10.150.30.222:6394","redis://10.150.30.223:6394")
                .setMasterName("mymaster")
                .setPassword("hengchang")
                .setScanInterval(5000);
        //2.根据Config创建出RedissonClient
        return Redisson.create(config);
    }
}
