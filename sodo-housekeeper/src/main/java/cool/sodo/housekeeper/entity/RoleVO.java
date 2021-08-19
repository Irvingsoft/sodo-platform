package cool.sodo.housekeeper.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cool.sodo.common.domain.Role;
import cool.sodo.common.util.StringUtil;
import cool.sodo.common.util.node.INode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现 INode 接口的 Role 视图层实体
 *
 * @author TimeChaser
 * @date 2021/8/19 15:39
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleVO extends Role implements INode {

    List<INode> children;

    @JsonIgnore
    private Boolean hasChildren;

    @Override
    public String getId() {
        return this.getRoleId();
    }

    @Override
    public List<INode> getChildren() {
        if (StringUtil.isEmpty(this.children)) {
            this.children = new ArrayList<>();
        }
        return this.children;
    }

    @Override
    public Boolean getHasChildren() {
        return this.getChildren().size() > 0;
    }
}
