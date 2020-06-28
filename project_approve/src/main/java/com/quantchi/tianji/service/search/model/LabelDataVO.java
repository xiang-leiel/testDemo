package com.quantchi.tianji.service.search.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Description 
 * @author leiel
 * @Date 2020/2/20 7:37 PM
 */
@Data
public class LabelDataVO {

    private String time;

    private List<AreaDataVO> areaDataVOS;

    private Map<Integer, List<Integer>> resultMap;

}
