package com.quantchi.tianji.service.search.model.vo;

import lombok.Data;

/**
 * @Description 
 * @author leiel
 * @Date 2019/12/19 10:42 AM
 */
@Data
public class EvaluateParam {

    /**
     * 项目id
     */
    private Long visitId;

    /**
     * 接待人id
     */
    private Long receiverId;

    /**
     * 招商人员id
     */
    private String staffId;

    /**
     * 是否为湖州或德清老乡
     */
    private String relation;

    /**
     * 是否知道德清
     */
    private String visited;

    /**
     * 是否知道生日
     */
    private String birthday;

    /**
     * 是否知道接待人学校
     */
    private String univesity;

    /**
     * 接待人性格
     */
    private String character;

    /**
     * 接待人爱好
     */
    private String favorite;

    /**
     * 接待人茶
     */
    private String tea;

    /**
     * 接待人酒
     */
    private String wine;

    /**
     * 接待人烟
     */
    private String tobacco;

    /**
     * 接待人饮食
     */
    private String diet;

    /**
     * 接待人标签1
     */
    private String labelOne;

    /**
     * 接待人家乡
     */
    private String hometown;

    /**
     * 接待人职业
     */
    private String profession;

    /**
     * 接待人办公室
     */
    private String office;

    /**
     * 接待人标签2
     */
    private String labelTwo;

    /**
     * 其他关于接待人备注
     */
    private String remark;

    /**
     * 接待人孩子情况
     */
    private String kids;

    /**
     * 孩子爱好
     */
    private String kidsFavorite;


}
