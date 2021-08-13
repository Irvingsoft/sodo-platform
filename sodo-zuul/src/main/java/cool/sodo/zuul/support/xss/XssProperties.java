package cool.sodo.zuul.support.xss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Xss配置类
 *
 * @author Chill
 */
@Data
@Component
@ConfigurationProperties(prefix = "sodo.xss")
public class XssProperties {

    /**
     * 开启xss
     */
    private Boolean enabled;

    /**
     * 放行url
     */
    private List<String> skipUrl = new ArrayList<>();

}
