package cool.sodo.user.entity;

import cool.sodo.common.base.domain.User;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UserRegisterDTO implements Serializable {

    @NotNull(message = "用户名不能为空")
    private String username;
    @NotNull(message = "密码不能为空")
    private String password;
    @NotNull(message = "手机号不能为空")
    private String phone;
    @NotNull(message = "手机验证码不能为空")
    private String code;

    public User toUser() {

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPhone(phone);
        return user;
    }
}
