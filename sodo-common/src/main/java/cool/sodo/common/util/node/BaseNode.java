package cool.sodo.common.util.node;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 节点基类
 *
 * @author Chill
 */
@Data
public class BaseNode implements INode {

    /**
     * 主键ID
     */
    protected String id;

    /**
     * 父节点ID
     */
    protected String parentId;

    /**
     * 子孙节点
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    protected List<INode> children = new ArrayList<>();

    /**
     * 是否有子孙节点
     */
    private Boolean hasChildren;

    /**
     * 是否有子孙节点
     */
    @Override
    public Boolean getHasChildren() {
        return children.size() > 0 || this.hasChildren;
    }
}
