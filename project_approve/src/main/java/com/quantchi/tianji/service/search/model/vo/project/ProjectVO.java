package com.quantchi.tianji.service.search.model.vo.project;

import lombok.Data;

import java.util.List;

/**
 * @Description 
 * @author leiel
 * @Date 2020/2/4 8:55 PM
 */
@Data
public class ProjectVO {

    private int total;

    private List<ProjectReportVO> projectReportVOs;

}
