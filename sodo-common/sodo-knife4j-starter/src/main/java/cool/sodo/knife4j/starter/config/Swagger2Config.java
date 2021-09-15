package cool.sodo.knife4j.starter.config;

import cool.sodo.knife4j.starter.property.Swagger2Property;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author TimeChaser
 * @date 2021/9/15 15:28
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({Swagger2Property.class})
@EnableSwagger2
@Slf4j
public class Swagger2Config {

    @Resource
    private Swagger2Property property;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.apiInfo())
                .select()
                //扫描哪个包
                .apis(RequestHandlerSelectors.basePackage(property.getBasePackage()))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(property.getTitle())
                .description(property.getDescription())
                .version(property.getVersion())
                .contact(new Contact(property.getName(), property.getUrl(), property.getEmail()))
                .build();
    }

    @PostConstruct
    public void log() {
        log.info("sodo-knife4j-starter 装配成功！");
    }
}
