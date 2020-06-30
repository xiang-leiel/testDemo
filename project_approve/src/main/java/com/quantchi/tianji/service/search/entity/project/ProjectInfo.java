package com.quantchi.tianji.service.search.entity.project;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 项目主表
 * </p>
 *
 * @author leiel
 * @since 2020-06-30
 */
@TableName("project_info")
public class ProjectInfo extends Model<ProjectInfo> {

    private static final long serialVersionUID=1L;

    /**
     * 项目id
     */
      private String id;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 项目状态代码
     */
    private Integer status;

    /**
     * 产业类型 1:一产 2:二产  3:三产
     */
    private Integer industryType;

    /**
     * 国家
     */
    private String country;

    /**
     * 行政区
     */
    private String region;

    /**
     * 项目内容
     */
    private String content;

    /**
     * 项目所需土地面积
     */
    private BigDecimal needLandArea;

    /**
     * 项目所需土地类型标记（1、存量  2、新征  3、待定  4、租赁厂房）
     */
    private Integer landType;

    /**
     * 需用地单位 1亩 2平方米
     */
    private Integer landUnit;

    /**
     * 项目所需办公面积（单位：平方米）
     */
    private BigDecimal officeArea;

    /**
     * 项目总投资
     */
    private BigDecimal investTotal;

    /**
     * 固定投资
     */
    private BigDecimal investFixed;

    /**
     * 货币单位代码
     */
    private Integer currencyUnit;

    /**
     * 投资规模
     */
    private Integer investScale;

    /**
     * 项目落地平台（拟）代码
     */
    private Integer landDeptId;

    /**
     * 项目所属招商组部门代码
     */
    private Integer relateDeptDm;

    /**
     * 项目其他要求条件
     */
    private String otherRequires;

    /**
     * 投资意向类型
     */
    private Integer investType;

    /**
     * 投资意向日期
     */
    private Date investDate;

    /**
     * 项目上报人id（用户代码），若多个，可用"|"分割
     */
    private String reportUserId;

    /**
     * 项目首报人id
     */
    private Integer masterUserDm;

    /**
     * 备注
     */
    private String mark;

    /**
     * 项目是否隐藏标记  0不隐藏 1隐藏
     */
    private Integer hideFlag;

    /**
     * 启用标记 1:启用 0:失效
     */
    private Integer isValid;

    /**
     * 上报时间
     */
    private Date reportTime;

    /**
     * 操作时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 操作员id
     */
    private Integer operatorId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIndustryType() {
        return industryType;
    }

    public void setIndustryType(Integer industryType) {
        this.industryType = industryType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BigDecimal getNeedLandArea() {
        return needLandArea;
    }

    public void setNeedLandArea(BigDecimal needLandArea) {
        this.needLandArea = needLandArea;
    }

    public Integer getLandType() {
        return landType;
    }

    public void setLandType(Integer landType) {
        this.landType = landType;
    }

    public Integer getLandUnit() {
        return landUnit;
    }

    public void setLandUnit(Integer landUnit) {
        this.landUnit = landUnit;
    }

    public BigDecimal getOfficeArea() {
        return officeArea;
    }

    public void setOfficeArea(BigDecimal officeArea) {
        this.officeArea = officeArea;
    }

    public BigDecimal getInvestTotal() {
        return investTotal;
    }

    public void setInvestTotal(BigDecimal investTotal) {
        this.investTotal = investTotal;
    }

    public BigDecimal getInvestFixed() {
        return investFixed;
    }

    public void setInvestFixed(BigDecimal investFixed) {
        this.investFixed = investFixed;
    }

    public Integer getCurrencyUnit() {
        return currencyUnit;
    }

    public void setCurrencyUnit(Integer currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    public Integer getInvestScale() {
        return investScale;
    }

    public void setInvestScale(Integer investScale) {
        this.investScale = investScale;
    }

    public Integer getLandDeptId() {
        return landDeptId;
    }

    public void setLandDeptId(Integer landDeptId) {
        this.landDeptId = landDeptId;
    }

    public Integer getRelateDeptDm() {
        return relateDeptDm;
    }

    public void setRelateDeptDm(Integer relateDeptDm) {
        this.relateDeptDm = relateDeptDm;
    }

    public String getOtherRequires() {
        return otherRequires;
    }

    public void setOtherRequires(String otherRequires) {
        this.otherRequires = otherRequires;
    }

    public Integer getInvestType() {
        return investType;
    }

    public void setInvestType(Integer investType) {
        this.investType = investType;
    }

    public Date getInvestDate() {
        return investDate;
    }

    public void setInvestDate(Date investDate) {
        this.investDate = investDate;
    }

    public String getReportUserId() {
        return reportUserId;
    }

    public void setReportUserId(String reportUserId) {
        this.reportUserId = reportUserId;
    }

    public Integer getMasterUserDm() {
        return masterUserDm;
    }

    public void setMasterUserDm(Integer masterUserDm) {
        this.masterUserDm = masterUserDm;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Integer getHideFlag() {
        return hideFlag;
    }

    public void setHideFlag(Integer hideFlag) {
        this.hideFlag = hideFlag;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
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

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ProjectInfo{" +
        "id=" + id +
        ", name=" + name +
        ", status=" + status +
        ", industryType=" + industryType +
        ", country=" + country +
        ", region=" + region +
        ", content=" + content +
        ", needLandArea=" + needLandArea +
        ", landType=" + landType +
        ", landUnit=" + landUnit +
        ", officeArea=" + officeArea +
        ", investTotal=" + investTotal +
        ", investFixed=" + investFixed +
        ", currencyUnit=" + currencyUnit +
        ", investScale=" + investScale +
        ", landDeptId=" + landDeptId +
        ", relateDeptDm=" + relateDeptDm +
        ", otherRequires=" + otherRequires +
        ", investType=" + investType +
        ", investDate=" + investDate +
        ", reportUserId=" + reportUserId +
        ", masterUserDm=" + masterUserDm +
        ", mark=" + mark +
        ", hideFlag=" + hideFlag +
        ", isValid=" + isValid +
        ", reportTime=" + reportTime +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", operatorId=" + operatorId +
        "}";
    }
}
