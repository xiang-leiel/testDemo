package com.quantchi.tianji.service.search.service.impl;
import com.quantchi.tianji.service.search.model.LeftTreeNode;
import com.quantchi.tianji.service.search.service.LeftTreeNodeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 左侧导航树对象的管理者
 * 包括导航树对象的缓存,克隆,转换等操作
 * @author whuang
 * @date 2019/1/24
 */
@Service
@CacheConfig(cacheNames = "leftTreeNodeCache")
public class LeftTreeNodeManager {

    //左侧导航树创建着
    @Autowired
    private LeftTreeNodeBuilder leftTreeNodeBuilder;

    /**
     * 以节点名称查找缓存中的树对象,克隆后返回
     * @return
     */
    public  Map<String,LeftTreeNode> getCloneLeftTreeNodeMapFromCache() {
        Map<String,LeftTreeNode> cloneLeftTreeNodeMapFromCache = new HashMap<>();
        Map<String,LeftTreeNode> leftTreeNodeMapFromCache = getLeftTreeNodeMapFromCache();
        for (String field : leftTreeNodeMapFromCache.keySet()) {
            //把克隆后的树对象插入返回结果中
            cloneLeftTreeNodeMapFromCache.put(field,cloneTreeNode(leftTreeNodeMapFromCache.get(field)));
        }
        return cloneLeftTreeNodeMapFromCache;
    }

    /**
     * 克隆树对象
     * @param root
     * @return
     */
    private LeftTreeNode cloneTreeNode(LeftTreeNode root) {
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

    /**
     * 获取缓存里的LeftTreeNode(一般是根节点)
     * @return
     */
    private  Map<String,LeftTreeNode> getLeftTreeNodeMapFromCache() {
        Map<String,LeftTreeNode> leftTreeNodeMapFromCache = new HashMap<>();
        List<LeftTreeNode> leftTreeNodeList = leftTreeNodeBuilder.buildLeftTreeNodeList();
        for (LeftTreeNode leftTreeNode : leftTreeNodeList) {
            leftTreeNodeMapFromCache.put(leftTreeNode.getField(),leftTreeNode);
        }
        return leftTreeNodeMapFromCache;
    }

    /**
     * 将root树转换成Map形式
     * 目的是方便根据name字段快速查找到节点
     * @param root 树对象
     * @return
     */
    public static Map<String,LeftTreeNode> expandLeftTreeNode2Map(LeftTreeNode root) {
        Map<String,LeftTreeNode> result = new HashMap<>();
        result.put(root.getName(),root);
        if(root.getChildren() == null || root.getChildren().isEmpty()) {
            return result;
        }
        for (LeftTreeNode child : root.getChildren()) {
            result.putAll(expandLeftTreeNode2Map(child));
        }
        return result;
    }

    /**
     * 统计各节点的文档数量
     * 将子节点的数量层层统计汇总到父节点
     */
    public static Long statisticsEveryParentNodeQuantity(LeftTreeNode root) {
        if(!root.getChildren().isEmpty()) {
            Long totalChildrenDocNum = 0L;
            for (LeftTreeNode child : root.getChildren()) {
                //递归计算
                Long childDocNum = statisticsEveryParentNodeQuantity(child);
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


}
