package cool.sodo.log.starter.config;

import cool.sodo.log.starter.aspect.BusinessLoggerAspect;
import cool.sodo.log.starter.listener.BusinessLogEventListener;
import cool.sodo.log.starter.listener.ErrorLogEventListener;
import cool.sodo.log.starter.listener.OauthApiLogEventListener;
import cool.sodo.log.starter.publisher.BusinessLogPublisher;
import cool.sodo.log.starter.publisher.ErrorLogPublisher;
import cool.sodo.log.starter.publisher.OauthApiLogPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author TimeChaser
 * @date 2021/9/14 11:23
 */
@Configuration(proxyBeanMethods = false)
public class LogConfig {

    @Bean
    public BusinessLoggerAspect businessLoggerAspect() {
        return new BusinessLoggerAspect();
    }

    @Bean
    public BusinessLogPublisher businessLogPublisher() {
        return new BusinessLogPublisher();
    }

    @Bean
    public ErrorLogPublisher errorLogPublisher() {
        return new ErrorLogPublisher();
    }

    @Bean
    public OauthApiLogPublisher oauthApiLogPublisher() {
        return new OauthApiLogPublisher();
    }

    @Bean
    public BusinessLogEventListener businessLogEventListener() {
        return new BusinessLogEventListener();
    }

    @Bean
    public ErrorLogEventListener errorLogEventListener() {
        return new ErrorLogEventListener();
    }

    @Bean
    public OauthApiLogEventListener oauthApiLogEventListener() {
        return new OauthApiLogEventListener();
    }
}
