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
@TableName("login_log")
public class LoginLog extends Model<LoginLog> {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 登录ip
     */
    private String ip;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    /**
     * 操作员代码
     */
    private Integer userDm;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }

    public Integer getUserDm() {
        return userDm;
    }

    public void setUserDm(Integer userDm) {
        this.userDm = userDm;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "LoginLog{" +
        "id=" + id +
        ", ip=" + ip +
        ", loginTime=" + loginTime +
        ", userDm=" + userDm +
        "}";
    }
}
