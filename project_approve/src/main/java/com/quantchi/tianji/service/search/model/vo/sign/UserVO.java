package com.quantchi.tianji.service.search.model.vo.sign;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description 
 * @author leiel
 * @Date 2019/12/9 8:36 PM
 */
@Data
public class UserVO {

    /** 招商人员编号 */
    private String staffId;

    /** 招商人员姓名 */
    private String staffName;

    /** 招商人员头像 */
    private String staffImg;

    /** 招商人员职位 */
    private String staffJob;

    /** 用户所属组别 */
    private String staffGroup;

    /** 用户组别区域 */
    private String groupLocRegion;

    /** 当前系统时间 */
    private Date nowTime;

    /** 用户今日签到次数 */
    private Integer signTimes;

    /** 用户本月签到率 */
    private BigDecimal signRate;

    /** 用户今日签到工时 */
    private BigDecimal hourToday;

    /** 用户今日行程距离 */
    private BigDecimal diatanceToday;

    /** 用户签到列表 */
    private List<Integer> signList;

    /** 用户签到次数列表 */
    private List<Integer> signTimesList;

    /** 当前用户今日已拜访数据(第一个为上班打卡的数据) */
    private List<VisitParam> visitRecord;

    /** 用户拜访起始数据 */
    private List<RouteVO> routeVOList;

    /** 是否提醒添加行程 0:不提醒 1:提醒 */
    private Integer addDistance;
}
