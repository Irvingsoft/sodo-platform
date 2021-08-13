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
@ConfigurationProperties("sodo.xss.url")
public class XssUrlProperties {

    private final List<String> excludePatterns = new ArrayList<>();

}
