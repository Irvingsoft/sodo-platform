package cool.sodo.catkin.client.annotation;

import cool.sodo.catkin.client.feign.impl.CatkinClientServiceFallbackFactory;
import cool.sodo.catkin.client.property.CatkinClientProperty;
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
@Import({CatkinClientProperty.class, CatkinClientServiceFallbackFactory.class})
public @interface EnableCatkinClient {
}
