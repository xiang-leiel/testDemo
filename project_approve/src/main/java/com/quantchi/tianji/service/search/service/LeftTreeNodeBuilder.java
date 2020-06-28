package com.quantchi.tianji.service.search.service;

import com.quantchi.tianji.service.search.model.LeftTreeNode;

import java.util.List;

/**
 * 针对不同项目,
 * 负责创建左侧导航栏树原始对象
 * @author whuang
 * @date 2019/6/13
 */
public interface LeftTreeNodeBuilder {

    /**
     * 创建左侧导航栏树
     * @return
     */
    List<LeftTreeNode> buildLeftTreeNodeList();
}
