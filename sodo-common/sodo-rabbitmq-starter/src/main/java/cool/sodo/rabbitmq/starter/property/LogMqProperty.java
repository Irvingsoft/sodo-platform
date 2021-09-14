package cool.sodo.rabbitmq.starter.property;

import cool.sodo.rabbitmq.starter.entity.MqProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author TimeChaser
 * @date 2021/8/12 15:19
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ConfigurationProperties("log-message")
public class LogMqProperty extends MqProperty {

    private String apiType;

    private String errorType;

    private String businessType;
}
