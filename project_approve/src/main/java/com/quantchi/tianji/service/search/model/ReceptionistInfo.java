package com.quantchi.tianji.service.search.model;

import java.util.Date;
import lombok.Data;

@Data
public class ReceptionistInfo {
    /**
    * id
    */
    private Long id;

    /**
    * 接待人姓名
    */
    private String name;

    /**
    * 0：男 1：女
    */
    private Integer sex;

    /**
    * 职位
    */
    private String job;

    /**
    * 0,"会议（展会、峰会等）"1,"校友会"2,"投资公司"3,"基金公司"4,"商会"5,"协会"6,"政府机构"7,"大学办事处"8,"中介"
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
    private Long visitId;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 更新时间
    */
    private Date updateTime;
}