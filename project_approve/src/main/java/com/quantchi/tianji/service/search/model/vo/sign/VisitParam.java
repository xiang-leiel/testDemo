package com.quantchi.tianji.service.search.model.vo.sign;

import lombok.Data;

import java.util.Date;

/**
 * @Description 
 * @author leiel
 * @Date 2019/12/9 8:49 PM
 */
@Data
public class VisitParam {

    /** 行程Id */
    private String projectId;

    /** 距离 */
    private String distance;

    /** 拜访名称 */
    private String visitName;

    /** 拜访地址 */
    private String visitLocation;

    /** 拜访玮度 */
    private String visitAte;

    /** 拜访经度 */
    private String visitLte;

    /** 拜访时间 */
    private Date visitTime;

    /** 是否超过5km标志位 0:未超出，1:超出*/
    private Integer overFlag;

    /** 是否为拜访数据*/
    private Integer visitFlag;

}
