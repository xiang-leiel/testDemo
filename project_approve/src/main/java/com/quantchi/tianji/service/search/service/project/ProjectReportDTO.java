package com.quantchi.tianji.service.search.service.project;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Description 
 * @author leiel
 * @Date 2020/2/3 1:02 PM
 */
@Data
public class ProjectReportDTO {

    //地区
    private List<Integer> regions;

    //上报开始时间
    private Date reportStartTime;

    //上报结束时间
    private Date reportEndTime;

    //投资规模
    private List<Integer> investUnits;

    //项目标签
    private List<Integer> projectLabels;

    //投资领域
    private List<Integer> fields;

    //项目所属部门
    private String department;

    //部门
    private List<Integer> departmentList;

    //项目名称
    private String projectName;

    //项目所属端口
    private String branch;

    //项目状态
    private List<Integer> statusList;

    //页
    private Integer page;

    //页数
    private Integer pageSize;

}
