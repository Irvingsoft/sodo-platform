package cool.sodo.housekeeper.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cool.sodo.common.base.domain.Menu;
import cool.sodo.common.base.util.StringUtil;
import cool.sodo.common.base.util.node.INode;
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

    /**
     * hasChildren 字段导致 avue-crud table 无法展开。
     * <p>
     * getHasChildren() 会自动生成 hasChildren 字段，遂在 MenuVO 中手动忽略。
     *
     * @date 2021/8/14 18:00
     */
    @JsonIgnore
    private Boolean hasChildren;

    /**
     * JSON 序列化时，调用所有 get 方法，因此返回结果中会加上 id 字段
     *
     * @return java.lang.String
     */
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
