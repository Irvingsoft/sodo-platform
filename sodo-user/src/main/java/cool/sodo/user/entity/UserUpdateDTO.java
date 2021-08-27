package cool.sodo.user.entity;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Data
public class UserUpdateDTO implements Serializable {

    private String name;
    private String nickname;
    private String avatarUrl;

    private String schoolId;

    @Max(value = 2, message = "性别参数无效")
    @Min(value = 0, message = "性别参数无效")
    private Integer gender;
}
