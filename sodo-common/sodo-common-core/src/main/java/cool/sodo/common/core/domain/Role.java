package cool.sodo.common.core.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.common.base.exception.SoDoException;
import cool.sodo.common.base.util.StringUtil;
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

    @TableId(type = IdType.ASSIGN_ID)
    private String roleId;

    private String parentId;

    private String clientId;

    private String name;

    private String code;

    private String description;

    private Integer sort;

    private Date createAt;

    private Date updateAt;

    private String createBy;

    private String updateBy;

    @TableLogic
    private Boolean deleted;

    public void init(String createBy) {

        this.createBy = createBy;
        if (!StringUtil.isEmpty(this.roleId) &&
                this.roleId.equals(this.parentId)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, "父角色不能为本身！");
        }
    }

    public void update(Role role, String updateBy) {

        this.parentId = role.getParentId();
        this.description = role.getDescription();
        if (!StringUtil.isEmpty(role.getName())) {
            this.name = role.getName();
        }
        if (!StringUtil.isEmpty(role.getSort())) {
            this.sort = role.getSort();
        }
        this.updateBy = updateBy;
        if (!StringUtil.isEmpty(this.roleId) &&
                this.roleId.equals(this.parentId)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, "父角色不能为本身！");
        }
    }

    public void delete(String updateBy) {
        this.updateBy = updateBy;
    }
}
