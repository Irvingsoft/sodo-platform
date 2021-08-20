package cool.sodo.auth.message;

import cool.sodo.common.entity.MqProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author TimeChaser
 * @date 2021/8/12 13:47
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Configuration
@ConfigurationProperties(prefix = "user-message")
public class UserMqProperty extends MqProperty {

    /**
     * 设定消息类型主体
     *
     * @date 2021/8/12 13:47
     */
    private String createType;
    private String updateType;
    private String deleteType;
    private String loginType;
    private String fullType;
}
