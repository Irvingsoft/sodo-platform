package cool.sodo.common.util.node;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 森林节点类
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode(callSuper = false)
class ForestNode extends BaseNode {

    private static final long serialVersionUID = 1L;

    /**
     * 节点内容
     */
    private Object content;

    public ForestNode(String id, String parentId, Object content) {
        this.id = id;
        this.parentId = parentId;
        this.content = content;
    }

}
