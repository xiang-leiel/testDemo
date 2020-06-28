package com.quantchi.tianji.service.search.model;

import com.quantchi.tianji.service.search.model.vo.project.ProjectReportVO;
import lombok.Data;

import java.util.List;

/**
 * @Description 
 * @author leiel
 * @Date 2020/2/23 11:29 PM
 */
@Data
public class AreaDataNewVO {

    /**
     * 招商区部门名称
     */
    private String areaName;

    /**
     * 招商区部门代码
     */
    private Integer areaDeptDm;

    /**
     * 签约数量
     */
    private Integer signCounts;

    /**
     * 在谈数量
     */
    private Integer unsignCounts;

    /**
     * 签约项目数据
     */
    private List<ProjectReportVO> signedData;

    /**
     * 在谈项目数据
     */
    private List<ProjectReportVO> unsignData;

}
