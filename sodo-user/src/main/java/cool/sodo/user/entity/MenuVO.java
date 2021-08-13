package cool.sodo.user.entity;

import cool.sodo.common.domain.Menu;
import cool.sodo.common.util.StringUtil;
import cool.sodo.common.util.node.INode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现 INode 接口的 Menu 视图层实体
 *
 * @author TimeChaser
 * @date 2021/7/26 17:30
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuVO extends Menu implements INode {

    List<INode> children;

    @Override
    public String getId() {
        return this.getMenuId();
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
