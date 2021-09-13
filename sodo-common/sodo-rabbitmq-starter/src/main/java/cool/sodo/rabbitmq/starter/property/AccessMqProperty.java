package cool.sodo.rabbitmq.starter.property;

import cool.sodo.rabbitmq.starter.entity.MqProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * API 访问消息
 *
 * @author TimeChaser
 * @date 2021/8/13 19:42
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Configuration
@ConditionalOnProperty(prefix = "access-message", name = "enabled", havingValue = "true")
@ConfigurationProperties("access-message")
public class AccessMqProperty extends MqProperty {

    private String apiType;

    private String ipType;
}
