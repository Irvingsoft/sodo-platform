package cool.sodo.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户角色与菜单的对应关系
 *
 * @author TimeChaser
 * @date 2021/7/21 14:00
 */
@Data
@TableName(value = "role_to_menu")
public class RoleToMenu implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String roleId;

    private String menuId;

    private Date createAt;
}
