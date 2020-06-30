package com.quantchi.tianji.service.search.entity.project;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 项目标签或投资领域或人才标签表
 * </p>
 *
 * @author leiel
 * @since 2020-06-30
 */
@TableName("label")
public class Label extends Model<Label> {

    private static final long serialVersionUID=1L;

    /**
     * 项目标签或行业领域代码
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 上级项目标签或行业领域代码
     */
    private Integer pid;

    /**
     * 项目标签或行业领域名称
     */
    private String name;

    /**
     * 1项目标签 2行业领域  3高层次人才  4人才引进
     */
    private Integer type;

    /**
     * 层级标记
     */
    private Integer level;

    /**
     * 数值标记，若为1则表示该字段需要输入数值
     */
    private Integer countFlag;

    /**
     * 单复选框标记(0:单选;1:复选)
     */
    private Integer choice;

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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getCountFlag() {
        return countFlag;
    }

    public void setCountFlag(Integer countFlag) {
        this.countFlag = countFlag;
    }

    public Integer getChoice() {
        return choice;
    }

    public void setChoice(Integer choice) {
        this.choice = choice;
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
        return "Label{" +
        "id=" + id +
        ", pid=" + pid +
        ", name=" + name +
        ", type=" + type +
        ", level=" + level +
        ", countFlag=" + countFlag +
        ", choice=" + choice +
        ", isValid=" + isValid +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", operatorId=" + operatorId +
        "}";
    }
}
