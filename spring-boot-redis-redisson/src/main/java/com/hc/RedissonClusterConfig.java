package com.hc;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * 单机 redisson 的配置
 */
@Configuration
public class RedissonClusterConfig {

    @Bean(destroyMethod="shutdown")
    public RedissonClient clusterRedissonClient() throws IOException {
        //1 创建配置
        Config config = new Config();
        ClusterServersConfig clusterServersConfig = config.useClusterServers();
        /**
         * 10.150.30.214:6394,10.150.30.215:6394,10.150.30.216:6394,
         * 10.150.30.217:6394,10.150.30.218:6394,10.150.30.219:6394
         * timeout: 3600
         * password: hengchang
         */
        clusterServersConfig.addNodeAddress("redis://10.150.30.214:6394","redis://10.150.30.215:6394","redis://10.150.30.216:6394",
                "redis://10.150.30.217:6394","redis://10.150.30.218:6394","redis://10.150.30.219:6394").setPassword("hengchang")
                .setScanInterval(5000);
        //2.根据Config创建出RedissonClient
        return Redisson.create(config);
    }
}
