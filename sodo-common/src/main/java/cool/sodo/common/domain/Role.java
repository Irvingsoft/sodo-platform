package cool.sodo.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户角色
 *
 * @author TimeChaser
 * @date 2021/7/21 13:39
 */
@Data
@TableName(value = "role")
public class Role implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    private String roleId;

    private String parentId;

    private String clientId;

    private String name;

    private String description;

    private Integer sort;

    private Date createAt;

    private Date updateAt;
}
