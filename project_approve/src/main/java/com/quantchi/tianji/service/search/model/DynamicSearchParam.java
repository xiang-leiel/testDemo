package com.quantchi.tianji.service.search.model;

import lombok.Data;

import java.util.List;

/**
 * @Description 
 * @author leiel
 * @Date 2020/2/22 9:53 AM
 */
@Data
public class DynamicSearchParam {

    private Integer topLabelId;

    private List<Integer> childLabelId;
}
