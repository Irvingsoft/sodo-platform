package cool.sodo.auth.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 授权请求实体
 *
 * @author TimeChaser
 * @date 2021/7/13 10:24
 */
@Data
public class AuthorizeRequest implements Serializable {

    @NotNull(message = "授权码不能为空")
    private String code;

    @NotNull(message = "授权方式不能为空")
    private String grantType;
}
