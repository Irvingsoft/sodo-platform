package cool.sodo.user.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 密码更新表单实体类
 *
 * @author TimeChaser
 * @date 2021/7/27 16:48
 */
@Data
public class PasswordUpdateRequest implements Serializable {

    private String oldPassword;

    private String newPassword;
}
