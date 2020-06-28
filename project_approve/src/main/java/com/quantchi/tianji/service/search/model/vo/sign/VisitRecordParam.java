package com.quantchi.tianji.service.search.model.vo.sign;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Description 
 * @author leiel
 * @Date 2019/12/10 1:57 PM
 */
@Data
public class VisitRecordParam implements Serializable {

    /** 拜访人 */
    private String staffId;

    /** 拜访人姓名 */
    private String staffName;

    /** 拜访同行人 */
    private String visitPartner;

    /** 拜访类型 */
    private Integer visitType;

    /** 拜访商会或会议名称 */
    private String visitName;

    /** 拜访地址 */
    private String visitLocation;

    /** 拜访玮度 */
    private Double visitAte;

    /** 拜访经度 */
    private Double visitLte;

    /** 拜访时间 */
    private String visitTime;

}
