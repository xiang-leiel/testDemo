package com.quantchi.tianji.service.search.model;

import lombok.Data;

/**
 * @Description 
 * @author leiel
 * @Date 2019/12/18 8:56 PM
 */
@Data
public class ReceiverParam {

    /**
     * 接待人姓名
     */
    private Long receiverId;

    /**
     * 接待人姓名
     */
    private String name;

    /**
     * 接待人性别
     */
    private Integer sex;

    /**
     * 来源类型
     */
    private Integer originType;

    /**
     * 来源名称
     */
    private String originName;

    /**
     * 关联名称
     */
    private String interrelateName;

    /**
     * 职位
     */
    private String job;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 微信号
     */
    private String wechat;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 工作地址
     */
    private String workAddress;

    /**
     * 头像Url
     */
    private String photoUrl;

    /**
     * 名片id
     */
    private String cardId;

    /**
     * 关联的招商员id
     */
    private String staffId;

    /**
     * 关联用户行程id或为公司id
     */
    private String visitId;

}
