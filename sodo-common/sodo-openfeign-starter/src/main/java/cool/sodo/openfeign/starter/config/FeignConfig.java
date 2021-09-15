package cool.sodo.openfeign.starter.config;

import feign.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author TimeChaser
 * @date 2021/9/15 11:34
 */
@Configuration(proxyBeanMethods = false)
@Slf4j
public class FeignConfig {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @PostConstruct
    public void log() {
        log.info("sodo-openfeign-starter 装载成功！");
    }
}