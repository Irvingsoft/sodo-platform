package cool.sodo.catkin.starter.property;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author TimeChaser
 * @date 2021/9/13 10:16
 */
@Data
@ConfigurationProperties(prefix = "catkin.client")
@Slf4j
public class CatkinClientProperty implements SmartInitializingSingleton {

    private String catkinToken;

    private String catkinBizType;

    @Override
    public void afterSingletonsInstantiated() {
        log.info("Catkin client property: {}", this);
    }
}
