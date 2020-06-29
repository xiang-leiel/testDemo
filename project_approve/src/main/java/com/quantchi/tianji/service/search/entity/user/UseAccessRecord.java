package com.quantchi.tianji.service.search.entity.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author leiel
 * @since 2020-06-29
 */
@TableName("use_access_record")
public class UseAccessRecord extends Model<UseAccessRecord> {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * ip
     */
    private String accessIp;

    /**
     * 用户访问接口
     */
    private String accessUrl;

    /**
     * 用户访问接口参数
     */
    private String accessParam;

    /**
     * 访问时间
     */
    private LocalDateTime accessTime;

    /**
     * 操作员代码
     */
    private Integer userDm;

    /**
     * 来源  1钉钉  2商务局web
     */
    private Integer source;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccessIp() {
        return accessIp;
    }

    public void setAccessIp(String accessIp) {
        this.accessIp = accessIp;
    }

    public String getAccessUrl() {
        return accessUrl;
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }

    public String getAccessParam() {
        return accessParam;
    }

    public void setAccessParam(String accessParam) {
        this.accessParam = accessParam;
    }

    public LocalDateTime getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(LocalDateTime accessTime) {
        this.accessTime = accessTime;
    }

    public Integer getUserDm() {
        return userDm;
    }

    public void setUserDm(Integer userDm) {
        this.userDm = userDm;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UseAccessRecord{" +
        "id=" + id +
        ", accessIp=" + accessIp +
        ", accessUrl=" + accessUrl +
        ", accessParam=" + accessParam +
        ", accessTime=" + accessTime +
        ", userDm=" + userDm +
        ", source=" + source +
        "}";
    }
}
