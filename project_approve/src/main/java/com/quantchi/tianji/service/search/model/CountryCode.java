package com.quantchi.tianji.service.search.model;

import lombok.Data;

import java.util.List;

/**
 * @Description 
 * @author leiel
 * @Date 2020/2/28 3:58 PM
 */
@Data
public class CountryCode {

    private String name;

    private Integer code;

    private List<RegionCode> regionCodeList;

}
