package com.quantchi.tianji.service.search.model;

import lombok.Data;

@Data
public class SettingValue {
    private Integer id;

    /**
    * 属性
    */
    private String type;

    /**
    * 属性值
    */
    private String value;

    /**
    * 附加值
    */
    private String remark;
}