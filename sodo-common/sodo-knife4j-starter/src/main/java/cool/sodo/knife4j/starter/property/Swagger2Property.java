package cool.sodo.knife4j.starter.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author TimeChaser
 * @date 2021/9/15 16:04
 */
@Data
@ConfigurationProperties(prefix = "knife4j", ignoreInvalidFields = true)
public class Swagger2Property {

    private String title;

    private String description;

    private String version;

    private String basePackage;

    private String name;

    private String url;

    private String email;
}
