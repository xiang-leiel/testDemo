package com.quantchi.tianji.service.search.utils;

import com.quantchi.tianji.service.search.model.LeftTreeNode;

/**
 * 导航树工具类
 * @author whuang
 * @date 2019/2/14
 */
public class TreeNodeUtils {

    private TreeNodeUtils() {}

    /**
     * 统计各节点的文档数量
     */
    public static Long nodeDocCount(LeftTreeNode root) {
        if(!root.getChildren().isEmpty()) {
            Long totalChildrenDocNum = 0L;
            for (LeftTreeNode child : root.getChildren()) {
                Long childDocNum = nodeDocCount(child);
                if(childDocNum != null) {
                    totalChildrenDocNum += childDocNum;
                }
            };
            if (root.getQuantity() == null || root.getQuantity() < totalChildrenDocNum) {
                if(totalChildrenDocNum > 0L) {
                    root.setQuantity(totalChildrenDocNum);
                }

            }
        }
        return root.getQuantity();
    }

    /**
     * 复制导航树对象
     */
    public static LeftTreeNode cloneTreeNode(LeftTreeNode root) {
        LeftTreeNode newNode = new LeftTreeNode();
        newNode.setId(root.getId());
        newNode.setField(root.getField());
        newNode.setName(root.getName());
        newNode.setParentId(root.getParentId());
        newNode.setQuantity(root.getQuantity());
        for (LeftTreeNode child : root.getChildren()) {
            newNode.getChildren().add(cloneTreeNode(child));
        }
        return newNode;

    }
}
