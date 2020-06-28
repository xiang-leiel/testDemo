package com.quantchi.tianji.service.search.entity.authority;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author leiel
 * @since 2020-02-08
 */
@TableName("deqing_authority")
public class DeqingAuthority extends Model<DeqingAuthority> {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
    @TableId("id")
    private Long id;

    /**
     * 手机号
     */
    @TableId("mobile")
    private String mobile;

    /**
     * 姓名
     */
    private String name;

    /**
     * 所在端口
     */
    private String branch;

    /**
     * 权限 1 2 3 4
     */
    private Integer authority;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Integer getAuthority() {
        return authority;
    }

    public void setAuthority(Integer authority) {
        this.authority = authority;
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
        return "DeqingAuthority{" +
        "id=" + id +
        ", mobile=" + mobile +
        ", name=" + name +
        ", branch=" + branch +
        ", authority=" + authority +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
