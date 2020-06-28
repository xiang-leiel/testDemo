package com.quantchi.tianji.service.search.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Description 
 * @author leiel
 * @Date 2020/2/12 8:54 PM
 */
@Data
public class AreaDataVO {

    /**
     * 招商区部门名称
     */
    private String areaName;

    /**
     * 招商区部门代码
     */
    private Integer areaDeptDm;

    /**
     * 本周上报  本周签约  总签约
     */
    private List<Integer> areaCounts;

    /**
     * 组
     */
    private List<GroupDataVO> groupDataVO;

    /**
     * 项目数据
     */
    private Map<String, Integer> projectData;

}
