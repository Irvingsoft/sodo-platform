package cool.sodo.housekeeper.entity;

import cool.sodo.common.domain.User;
import cool.sodo.common.util.BeanUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserInsertDTO implements Serializable {

    private String username;
    private String password;
    private String name;
    private String nickname;
    private String phone;
    private String email;
    private String clientId;
    private Integer gender;
    private String description;

    private List<String> roleIdList;

    public User toUser() {
        return BeanUtil.copy(this, User.class);
    }
}
