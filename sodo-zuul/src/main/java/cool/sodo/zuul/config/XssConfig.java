package cool.sodo.zuul.config;

import cool.sodo.zuul.filter.XssFilter;
import cool.sodo.zuul.support.xss.XssProperties;
import cool.sodo.zuul.support.xss.XssUrlProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.annotation.Resource;
import javax.servlet.DispatcherType;

/**
 * 防止 XSS 攻击
 *
 * @author TimeChaser
 * @date 2021/8/3 20:28
 */
@Configuration
@ConditionalOnProperty(value = "sodo.xss.enabled", havingValue = "true")
public class XssConfig {

    @Resource
    private XssProperties xssProperties;
    @Resource
    private XssUrlProperties xssUrlProperties;

    @Bean
    public FilterRegistrationBean<XssFilter> xssFilterRegistration() {
        FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter(xssProperties, xssUrlProperties));
        registration.addUrlPatterns("/*");
        registration.setName("xssFilter");
        registration.setOrder(Ordered.LOWEST_PRECEDENCE);
        return registration;
    }
}
