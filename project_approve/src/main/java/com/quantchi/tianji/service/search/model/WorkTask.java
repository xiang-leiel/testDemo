package com.quantchi.tianji.service.search.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: DeQing-InvestmentEnterprise
 * @description:
 * @author: mf
 * @create: 2019-07-09 09:54
 **/
@Data
public class WorkTask extends AbstractWorkCircle implements Serializable {

    private Integer id;

    private String staffId;

    private String staffName;

    private String handlerId;

    private String handlerName;

    private String companyId;

    private String companyName;

    private String remark;

    private Integer isRead;

    private String from;

    private Map<String,Object> company;

    private String isAccept;

    private String status;

    private String origin;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}
