package com.quantchi.tianji.service.search.model;

import lombok.Data;

import java.util.List;

/**
 * @Description 
 * @author leiel
 * @Date 2020/2/13 8:55 AM
 */
@Data
public class ReportListParam {

    private List<String> projectIds;

    private Integer userId;

}
