package com.quantchi.tianji.service.search.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @program: DeQing-InvestmentEnterprise
 * @description:
 * @author: mf
 * @create: 2019-07-10 20:21
 **/
@Data
public class WorkCircleImpl extends AbstractWorkCircle {

    private Integer id;

    private String handlerId;

    private String handlerName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String remark;

    private String companyId;

    private String companyName;

    private String staffId;

    private String status;

    private String staffName;

    private String from;

    private String origin;

}
