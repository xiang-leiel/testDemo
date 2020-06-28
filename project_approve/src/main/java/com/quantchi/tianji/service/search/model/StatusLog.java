package com.quantchi.tianji.service.search.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * status_log
 * @author 
 */
@Data
public class StatusLog extends AbstractWorkCircle implements Serializable {
    private Long id;

    /**
     * 招商人员id
     */
    private String staffId;

    /**
     * 企业id
     */
    private String companyId;

    /**
     * 意向状态
     */
    private String intentionStatus;

    /**
     * 意向搬迁时间
     */
    private String intentionMoveTime;

    /**
     * 客户对接状态
     */
    private String status;

    /**
     * 需求土地
     */
    private String needLand;

    /**
     * 需求办公面积
     */
    private String needOffice;

    /**
     * 需求资金
     */
    private String needMoney;

    /**
     * 隐性条件
     */
    private String needCondition;

    /**
     * 办公场地数
     */
    private Integer officeNumber;

    private Integer officeArea;

    /**
     * 员工数
     */
    private Integer employeeNumber;

    /**
     * 主要的产品或服务
     */
    private String mainProduct;

    private String origin;

    /**
     * 风险和问题
     */
    private String risk;

    private String from;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createTime;

    private String picture;

}