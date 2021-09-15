package cool.sodo.common.core.config;

import cool.sodo.common.core.property.ServiceInfo;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author TimeChaser
 * @date 2021/9/15 11:27
 */
@Configuration(proxyBeanMethods = false)
@ComponentScan("cool.sodo.common.core.*")
@EnableConfigurationProperties({ServiceInfo.class})
public class CoreConfig {
}
