package com.hc;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Connector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextClosedEvent;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@Slf4j
public class RedissonSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedissonSpringBootApplication.class, args);
    }

    @Bean
    public GracefulShutdown gracefulShutdown() {
        return new GracefulShutdown();
    }

    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        log.info("开始调用 TomcatServletWebServerFactory，添加自定义的优雅关机方法 ");
        tomcat.addConnectorCustomizers(gracefulShutdown());
        return tomcat;
    }



    private class GracefulShutdown implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {
        private volatile Connector connector;
        private final int waitTime = 30;
        @Override
        public void customize(Connector connector) {
            this.connector = connector;
        }
        @Override
        public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
            log.info("优雅关机进行中, 连接停止，并关闭连接池。" + new Date());
            this.connector.pause();
            Executor executor = this.connector.getProtocolHandler().getExecutor();
            if (executor instanceof ThreadPoolExecutor) {
                try {
                    ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
                    threadPoolExecutor.shutdown();
                    if (!threadPoolExecutor.awaitTermination(waitTime, TimeUnit.SECONDS)) {
                        log.warn("Tomcat thread pool did not shut down gracefully within " + waitTime + " seconds. Proceeding with forceful shutdown");
                    }
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
            log.info("已经完成关机。" + new Date());
        }
    }


}
