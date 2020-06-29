package com.quantchi.tianji.service.search.entity.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author leiel
 * @since 2020-06-29
 */
@TableName("user_info")
public class UserInfo extends Model<UserInfo> {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 职务
     */
    private String job;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 所在部门代码
     */
    private Integer deptId;

    /**
     * 是否有效 1:有效 0:失效
     */
    private Integer isValid;

    /**
     * 考勤标记  1:考勤 0:非考勤
     */
    private Integer workFlag;

    /**
     * 操作时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 登录密码
     */
    private String loginPwd;

    /**
     * inv_ding_user表id
     */
    private String dingUserid;

    /**
     * wx_user表的open_id字段
     */
    private String wxOpenid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Integer getWorkFlag() {
        return workFlag;
    }

    public void setWorkFlag(Integer workFlag) {
        this.workFlag = workFlag;
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

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getDingUserid() {
        return dingUserid;
    }

    public void setDingUserid(String dingUserid) {
        this.dingUserid = dingUserid;
    }

    public String getWxOpenid() {
        return wxOpenid;
    }

    public void setWxOpenid(String wxOpenid) {
        this.wxOpenid = wxOpenid;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
        "id=" + id +
        ", name=" + name +
        ", job=" + job +
        ", mobile=" + mobile +
        ", deptId=" + deptId +
        ", isValid=" + isValid +
        ", workFlag=" + workFlag +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", loginPwd=" + loginPwd +
        ", dingUserid=" + dingUserid +
        ", wxOpenid=" + wxOpenid +
        "}";
    }
}
