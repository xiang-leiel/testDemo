package com.quantchi.tianji.service.search.model.vo;

import com.quantchi.tianji.service.search.model.StaffInfo;
import lombok.Data;

import java.util.Map;

/**
 * @Description 
 * @author leiel
 * @Date 2020/2/14 2:14 PM
 */
@Data
public class ReportDataCountVO extends StaffInfo {

    private Integer reportTotal;

    private Integer oneIndustry;

    private Integer twoIndustry;

    private Integer threeIndustry;

    private Integer talent;

    private Integer shanghaiOverFive;

    private String reportTime;

    /**
     * 投资规模数据
     */
    private Map<String, Integer> investData;
}
