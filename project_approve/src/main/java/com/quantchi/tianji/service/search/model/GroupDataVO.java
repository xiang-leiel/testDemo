package com.quantchi.tianji.service.search.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Description 
 * @author leiel
 * @Date 2020/2/12 8:55 PM
 */
@Data
public class GroupDataVO {

    /**
     * 组部门名称
     */
    private String groupName;

    /**
     * 组部门代码
     */
    private Integer groupDeptDm;

    /**
     * 本周上报  本周签约  总签约
     */
    private List<Integer> groupCounts;

    /**
     * 项目数据
     */
    private Map<String, Integer> projectData;

}
