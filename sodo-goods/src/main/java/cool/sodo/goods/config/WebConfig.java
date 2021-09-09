package cool.sodo.goods.config;

import cool.sodo.common.core.resolver.CurrentUserArgumentResolver;
import cool.sodo.goods.resolver.CurrentShopArgumentResolver;
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

    @Bean
    public CurrentShopArgumentResolver currentShopArgumentResolver() {
        return new CurrentShopArgumentResolver();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(currentUserArgumentResolver());
        resolvers.add(currentShopArgumentResolver());
    }

}
