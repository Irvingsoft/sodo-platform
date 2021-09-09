package cool.sodo.rabbitmq.property;

import cool.sodo.rabbitmq.entity.MqProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author TimeChaser
 * @date 2021/8/12 15:19
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Configuration
@ConditionalOnProperty(prefix = "log-message", name = "enabled", havingValue = "true")
@ConfigurationProperties("log-message")
public class LogMqProperty extends MqProperty {

    private String apiType;

    private String errorType;

    private String businessType;
}
