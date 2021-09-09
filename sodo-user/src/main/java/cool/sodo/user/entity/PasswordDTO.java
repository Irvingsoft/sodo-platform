package cool.sodo.user.entity;

import cool.sodo.common.base.entity.Constants;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 密码更新表单实体类
 *
 * @author TimeChaser
 * @date 2021/7/27 16:48
 */
@Data
public class PasswordDTO implements Serializable {

    @NotBlank
    private String oldPassword;

    @Pattern(regexp = Constants.PASSWORD_REGEXP)
    private String newPassword;
}
