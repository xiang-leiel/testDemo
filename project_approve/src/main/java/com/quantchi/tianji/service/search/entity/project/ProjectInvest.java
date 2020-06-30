package com.quantchi.tianji.service.search.entity.project;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 项目投资方信息表
 * </p>
 *
 * @author leiel
 * @since 2020-06-30
 */
@TableName("project_invest")
public class ProjectInvest extends Model<ProjectInvest> {

    private static final long serialVersionUID=1L;

    /**
     * 投资方id
     */
      private String id;

    /**
     * 关联项目id
     */
    private String projectId;

    /**
     * 投资方名称
     */
    private String name;

    /**
     * 投资方简介
     */
    private String introduction;

    /**
     * 投资方联系人姓名，多值名用“|”分隔
     */
    private String relateUserName;

    /**
     * 投资方联系人电话，多值名用“|”分隔
     */
    private String relateUserMobile;

    /**
     * 投资方联系人职位，多值名用“|”分隔
     */
    private String relateUserJob;

    /**
     * 国家
     */
    private String country;

    /**
     * 行政区
     */
    private String region;

    /**
     * 是否有效 1:有效 0:失效
     */
    private Integer isValid;

    /**
     * 操作时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getRelateUserName() {
        return relateUserName;
    }

    public void setRelateUserName(String relateUserName) {
        this.relateUserName = relateUserName;
    }

    public String getRelateUserMobile() {
        return relateUserMobile;
    }

    public void setRelateUserMobile(String relateUserMobile) {
        this.relateUserMobile = relateUserMobile;
    }

    public String getRelateUserJob() {
        return relateUserJob;
    }

    public void setRelateUserJob(String relateUserJob) {
        this.relateUserJob = relateUserJob;
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

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
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
        return "ProjectInvest{" +
        "id=" + id +
        ", projectId=" + projectId +
        ", name=" + name +
        ", introduction=" + introduction +
        ", relateUserName=" + relateUserName +
        ", relateUserMobile=" + relateUserMobile +
        ", relateUserJob=" + relateUserJob +
        ", country=" + country +
        ", region=" + region +
        ", isValid=" + isValid +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", operatorId=" + operatorId +
        "}";
    }
}
