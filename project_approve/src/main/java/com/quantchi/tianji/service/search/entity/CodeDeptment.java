package com.quantchi.tianji.service.search.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 部门或落地部门表
 * </p>
 *
 * @author leiel
 * @since 2020-07-01
 */
@TableName("code_deptment")
public class CodeDeptment extends Model<CodeDeptment> {

    private static final long serialVersionUID=1L;

    /**
     * 部门代码
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 部门类型: 1:组  2片区  3:平台  4.落地平台  5县级部门
     */
    private Integer type;

    /**
     * 上级部门代码
     */
    private Integer pid;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 部门所在片区 1:北京  2:上海  3:深圳  4:杭州  5:合肥  6:南京  7:武汉
     */
    private String region;

    /**
     * 启用标记 1:启用 0:失效
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
     * 部门排序
     */
    private Integer sort;

    /**
     * 部门关联角色
     */
    private Integer role;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CodeDeptment{" +
        "id=" + id +
        ", type=" + type +
        ", pid=" + pid +
        ", name=" + name +
        ", region=" + region +
        ", isValid=" + isValid +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", sort=" + sort +
        ", role=" + role +
        "}";
    }
}
