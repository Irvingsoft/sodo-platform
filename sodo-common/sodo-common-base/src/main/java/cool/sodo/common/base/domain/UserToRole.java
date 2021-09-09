package cool.sodo.common.base.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户 角色 对应关系
 *
 * @author TimeChaser
 * @date 2021/7/21 15:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "user_to_role")
public class UserToRole implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String userId;

    private String roleId;

    private Date createAt;

    public UserToRole(String userId, String roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}
