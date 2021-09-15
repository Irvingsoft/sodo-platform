package cool.sodo.log.starter.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author TimeChaser
 * @date 2021/9/14 11:23
 */
@Configuration(proxyBeanMethods = false)
@ComponentScan("cool.sodo.log.starter.*")
@Slf4j
public class LogConfig {

    @PostConstruct
    public void log() {
        log.info("sodo-log-starter 装载成功！");
    }
}
