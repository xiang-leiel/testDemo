package com.quantchi.tianji.service.search.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 国家编码：iso-3166-1标准
 * </p>
 *
 * @author leiel
 * @since 2020-07-02
 */
@TableName("code_country")
public class CodeCountry extends Model<CodeCountry> {

    private static final long serialVersionUID=1L;

    /**
     * 国家代码
     */
      private Integer id;

    /**
     * ISO英文名称
     */
    private String englishName;

    /**
     * 国家中文名称
     */
    private String name;

    /**
     * 二位字母代码
     */
    private String twoShortName;

    /**
     * 三位字母代码
     */
    private String threeShortName;

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

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTwoShortName() {
        return twoShortName;
    }

    public void setTwoShortName(String twoShortName) {
        this.twoShortName = twoShortName;
    }

    public String getThreeShortName() {
        return threeShortName;
    }

    public void setThreeShortName(String threeShortName) {
        this.threeShortName = threeShortName;
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
        return "CodeCountry{" +
        "id=" + id +
        ", englishName=" + englishName +
        ", name=" + name +
        ", twoShortName=" + twoShortName +
        ", threeShortName=" + threeShortName +
        ", isValid=" + isValid +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", operatorId=" + operatorId +
        "}";
    }
}
