package com.quantchi.tianji.service.search.entity.project;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.models.auth.In;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author leiel
 * @since 2020-02-01
 */
@TableName("project_report")
public class ProjectReport extends Model<ProjectReport> {

    private static final long serialVersionUID=1L;

    /**
     * 关联项目id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 项目状态 0:初审，1:复审，2:终审
     */
    private Integer status;

    /**
     * 产业类型
     */
    private String industryType;

    /**
     * 项目标签
     */
    private String projectLabel;

    /**
     * 行业领域
     */
    private String field;

    /**
     * 投资方简介
     */
    private String investInfo;

    /**
     * 项目内容
     */
    private String projectInfo;

    /**
     * 需用地
     */
    private String needLand;

    /**
     * 固定资产投资
     */
    private String assetInvest;

    /**
     * 研判标签
     */
    private String judgeLabel;

    /**
     * 签约标签
     */
    private String signLabel;

    /**
     * 初步研判建议待落地平台和预评价建议待落地平台
     */
    private String suggestLand;

    /**
     * 审核人
     */
    private String auditor;

    /**
     * 审核备注
     */
    private String auditRemark;

    /**
     * 当前流程节点
     */
    private String flowWork;

    /**
     * 逻辑删除
     */
    private String isDelete;

    /**
     * 审核时间
     */
    private Date auditTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 项目所在地区
     */
    private String region;

    /**
     * 具体投资额
     */
    private Integer investmentUnit;

    /**
     * 项目所属端口
     */
    private String branch;

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Integer getInvestmentUnit() {
        return investmentUnit;
    }

    public void setInvestmentUnit(Integer investmentUnit) {
        this.investmentUnit = investmentUnit;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public String getProjectLabel() {
        return projectLabel;
    }

    public void setProjectLabel(String projectLabel) {
        this.projectLabel = projectLabel;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getInvestInfo() {
        return investInfo;
    }

    public void setInvestInfo(String investInfo) {
        this.investInfo = investInfo;
    }

    public String getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(String projectInfo) {
        this.projectInfo = projectInfo;
    }

    public String getNeedLand() {
        return needLand;
    }

    public void setNeedLand(String needLand) {
        this.needLand = needLand;
    }

    public String getAssetInvest() {
        return assetInvest;
    }

    public void setAssetInvest(String assetInvest) {
        this.assetInvest = assetInvest;
    }

    public String getJudgeLabel() {
        return judgeLabel;
    }

    public void setJudgeLabel(String judgeLabel) {
        this.judgeLabel = judgeLabel;
    }

    public String getSignLabel() {
        return signLabel;
    }

    public void setSignLabel(String signLabel) {
        this.signLabel = signLabel;
    }

    public String getSuggestLand() {
        return suggestLand;
    }

    public void setSuggestLand(String suggestLand) {
        this.suggestLand = suggestLand;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getAuditRemark() {
        return auditRemark;
    }

    public void setAuditRemark(String auditRemark) {
        this.auditRemark = auditRemark;
    }

    public String getFlowWork() {
        return flowWork;
    }

    public void setFlowWork(String flowWork) {
        this.flowWork = flowWork;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ProjectReport{" +
        "id=" + id +
        ", status=" + status +
        ", industryType=" + industryType +
        ", projectLabel=" + projectLabel +
        ", field=" + field +
        ", investInfo=" + investInfo +
        ", projectInfo=" + projectInfo +
        ", needLand=" + needLand +
        ", assetInvest=" + assetInvest +
        ", judgeLabel=" + judgeLabel +
        ", signLabel=" + signLabel +
        ", suggestLand=" + suggestLand +
        ", auditor=" + auditor +
        ", auditRemark=" + auditRemark +
        ", flowWork=" + flowWork +
        ", isDelete=" + isDelete +
        ", auditTime=" + auditTime +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
