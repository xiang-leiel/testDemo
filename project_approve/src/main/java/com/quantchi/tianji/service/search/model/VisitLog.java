package com.quantchi.tianji.service.search.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * visit_log
 * @author 
 */
public class VisitLog implements Serializable {
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
     * 拜访反馈
     */
    private String feedback;

    /**
     * 图片
     */
    private String image;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;


    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}