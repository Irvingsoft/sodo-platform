package cool.sodo.user.entity;

import cool.sodo.common.domain.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegister implements Serializable {

    private String username;
    private String password;
    private String phone;
    private String code;

    public User toUser() {

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPhone(phone);
        return user;
    }
}
