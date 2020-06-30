package com.quantchi.tianji.service.search.entity.project;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 项目标签关联表
 * </p>
 *
 * @author leiel
 * @since 2020-06-30
 */
@TableName("project_label")
public class ProjectLabel extends Model<ProjectLabel> {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
      private String id;

    /**
     * 项目基本信息表id
     */
    private String projectId;

    /**
     * 项目标签或领域id
     */
    private Integer labelId;

    /**
     *  标签类型里输入的数值
     */
    private Integer labelCount;

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

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public Integer getLabelCount() {
        return labelCount;
    }

    public void setLabelCount(Integer labelCount) {
        this.labelCount = labelCount;
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
        return "ProjectLabel{" +
        "id=" + id +
        ", projectId=" + projectId +
        ", labelId=" + labelId +
        ", labelCount=" + labelCount +
        ", isValid=" + isValid +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", operatorId=" + operatorId +
        "}";
    }
}
