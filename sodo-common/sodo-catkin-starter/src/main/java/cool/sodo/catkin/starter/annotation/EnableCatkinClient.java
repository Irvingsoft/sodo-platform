package cool.sodo.catkin.starter.annotation;

import cool.sodo.catkin.starter.feign.impl.CatkinClientServiceFallbackFactory;
import cool.sodo.catkin.starter.generator.MybatisKeyGenerator;
import cool.sodo.catkin.starter.property.CatkinClientProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author TimeChaser
 * @date 2021/9/13 15:31
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableFeignClients(basePackages = "cool.sodo")
@EnableConfigurationProperties({CatkinClientProperty.class})
@Import({CatkinClientServiceFallbackFactory.class, MybatisKeyGenerator.class})
public @interface EnableCatkinClient {
}
