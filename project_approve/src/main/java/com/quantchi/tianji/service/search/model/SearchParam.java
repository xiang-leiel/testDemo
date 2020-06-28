package com.quantchi.tianji.service.search.model;

import lombok.Data;

import java.util.List;

/**
 * @Description 
 * @author leiel
 * @Date 2020/2/22 10:38 AM
 */
@Data
public class SearchParam {

    private List<DynamicSearchParam> dynamicSearchParam;

    private Integer userId;

    private Integer page;

    private Integer pageSize;
}
