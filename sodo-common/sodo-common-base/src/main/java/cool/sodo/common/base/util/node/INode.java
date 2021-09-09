package cool.sodo.common.base.util.node;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Blade.
 *
 * @author Chill
 */
public interface INode extends Serializable {

    /**
     * 主键
     *
     * @return Integer
     */
    String getId();

    /**
     * 父主键
     *
     * @return Integer
     */
    String getParentId();

    /**
     * 子孙节点
     *
     * @return List
     */
    List<INode> getChildren();

    /**
     * 是否有子孙节点
     *
     * @return Boolean
     */
    default Boolean getHasChildren() {
        return false;
    }

}
