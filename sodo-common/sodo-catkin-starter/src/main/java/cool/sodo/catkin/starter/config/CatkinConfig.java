package cool.sodo.catkin.starter.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author TimeChaser
 * @date 2021/9/15 14:49
 */
@Configuration(proxyBeanMethods = false)
@Slf4j
public class CatkinConfig {

    @PostConstruct
    public void log() {
        log.info("sodo-catkin-starter 装载成功！");
    }
}
