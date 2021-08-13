package cool.sodo.user.config;

import cool.sodo.common.resolver.CurrentUserArgumentResolver;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 1、Jackson 转 HTTP 消息的编码方式
 * 2、国际化信息支持，没有具体配置
 * 3、注入 RestTemplate
 * <p>
 * 不能通过包扫描注册 CurrentUserArgumentResolver 的原因可能是：
 * 1、注册成 Component 或者 Service 的顺位可能在 RedisCacheHelper 之后
 * 2、而直接在 Configuration 中注册，顺位是最高的
 * 3、顺位高于 RedisCacheHelper，才能完整地调用注册了的它？？？
 *
 * @author TimeChaser
 * @date 2020/11/5 11:31 下午
 */


@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder;

    public WebConfig(Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder) {
        this.jackson2ObjectMapperBuilder = jackson2ObjectMapperBuilder;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        converter.setObjectMapper(jackson2ObjectMapperBuilder.build());
        return converter;
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages/messages");
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        return messageSource;
    }

    @Bean
    public CurrentUserArgumentResolver currentUserArgumentResolver() {
        return new CurrentUserArgumentResolver();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(currentUserArgumentResolver());
    }

}
