package cool.sodo.zuul.config;

import cool.sodo.zuul.filter.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * zuul 最重要的部分，启用全局认证，拦截非法请求
 * <p>
 * configure(HttpSecurity http) 没有起到实际作用，因为 客户端 和 授权过滤使用的自定义规则
 * 过滤顺序：
 * 1. 把请求实体更换成自定义的请求包装器
 * 2. OauthClient
 * 3. OauthIp
 * 4. ApiSignature
 * <p>
 * OauthApi 的过滤交给 sodo-common 包
 *
 * @author TimeChaser
 * @date 2020/11/2 12:24 上午
 */
@Configuration
public class SecurityConfig {

    @Bean
    public ExceptionFilter exceptionFilter() {
        return new ExceptionFilter();
    }

    @Bean
    public HttpTraceFilter httpTraceFilter() {
        return new HttpTraceFilter();
    }

    @Bean
    public OauthClientFilter oauthClientFilter() {
        return new OauthClientFilter();
    }

    @Bean
    public OauthIpFilter oauthIpFilter() {
        return new OauthIpFilter();
    }

    @Bean
    public ApiSignatureFilter apiSignFilter() {
        return new ApiSignatureFilter();
    }
}