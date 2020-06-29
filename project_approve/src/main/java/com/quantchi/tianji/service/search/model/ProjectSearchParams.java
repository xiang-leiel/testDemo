package com.quantchi.tianji.service.search.model;

import lombok.Data;

import java.util.List;

/**
 * @Description 
 * @author leiel
 * @Date 2020/6/29 10:29 AM
 */
@Data
public class ProjectSearchParams {

    //地区
    private List<String> regions;

    //上报开始时间
    private String reportStartTime;

    //上报结束时间
    private String reportEndTime;

    //投资规模
    private List<Integer> investUnits;

    //项目标签
    private List<Integer> projectLabels;

    //投资领域
    private List<Integer> fields;

    //项目状态代码
    private List<Integer> statusList;

    //部门id
    private Integer departmentId;

    //用户id
    private Integer userId;

    //用户staffId
    private String staffId;

    //0待办  1终审
    private Integer dealFlag;

    //项目名称
    private String projectName;

    //页
    private Integer page;

    //页数
    private Integer pageSize;

}
