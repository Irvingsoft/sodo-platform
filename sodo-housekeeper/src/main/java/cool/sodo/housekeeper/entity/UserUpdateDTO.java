package cool.sodo.housekeeper.entity;

import cool.sodo.common.domain.User;
import cool.sodo.common.util.BeanUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserUpdateDTO implements Serializable {

    private String userId;
    private String name;
    private String nickname;
    private String phone;
    private String email;
    private String gender;
    private String description;

    private List<String> roleIdList;

    public User toUser() {
        return BeanUtil.copy(this, User.class);
    }
}
