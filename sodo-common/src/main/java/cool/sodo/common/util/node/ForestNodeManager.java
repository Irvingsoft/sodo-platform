package cool.sodo.common.util.node;

import cool.sodo.common.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 森林管理类
 *
 * @author Chill
 */
public class ForestNodeManager<T extends INode> {

    /**
     * 森林的所有节点
     */
    private final List<T> list;

    /**
     * 森林的父节点ID
     */
    private final List<String> parentIdList = new ArrayList<>();

    public ForestNodeManager(List<T> items) {
        list = items;
    }

    /**
     * 根据节点ID获取一个节点
     *
     * @param id 节点ID
     * @return 对应的节点对象
     */
    public INode getTreeNodeAT(String id) {
        for (INode forestNode : list) {
            if (forestNode.getId().equals(id)) {
                return forestNode;
            }
        }
        return null;
    }

    /**
     * 增加父节点ID
     *
     * @param parentId 父节点ID
     */
    public void addParentId(String parentId) {
        parentIdList.add(parentId);
    }

    /**
     * 获取树的根节点(一个森林对应多颗树)
     *
     * @return 树的根节点集合
     */
    public List<T> getRoot() {
        List<T> roots = new ArrayList<>();
        for (T forestNode : list) {
            if (StringUtil.isEmpty(forestNode.getParentId()) || parentIdList.contains(forestNode.getId())) {
                roots.add(forestNode);
            }
        }
        return roots;
    }

}
