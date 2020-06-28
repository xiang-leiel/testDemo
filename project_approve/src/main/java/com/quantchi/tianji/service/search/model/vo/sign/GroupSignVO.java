package com.quantchi.tianji.service.search.model.vo.sign;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description 
 * @author leiel
 * @Date 2019/12/10 8:36 PM
 */
@Data
public class GroupSignVO {

    /** 组名称*/
    private String groupName;

    /** 组驻点地址*/
    private String groupLocation;

    /** 组驻点地址所属地区*/
    private String groupLocRegion;

    /** 组平均签到率*/
    private BigDecimal groupSignRate;

    /** 组平均签到排名*/
    private int groupSignRank;

    /** 组平均签到行程*/
    private BigDecimal groupSignDistance;

    /** 组平均签到行程排名*/
    private int groupSignDistanceRank;

    /** 组平均拜访数*/
    private BigDecimal groupVisitCount;

    /** 组下所有用户信息 */
    private List<UserVO> userVOList;

    /** 组下所有用户每日签到数 */
    private List<BigDecimal> groupSignList;

    /** 组下所有用户每日行程数 */
    private List<BigDecimal> groupDistanceList;

    /** 组下所有用户每日拜访数 */
    private List<BigDecimal> groupVisitList;

    /** 组签到环比 */
    private BigDecimal groupSignCyc;

    /** 组行程环比 */
    private BigDecimal groupDistanceCyc;

    /** 组拜访环比 */
    private BigDecimal groupVisitCyc;

    /** 组签到总数 */
    private Integer groupSignTotal;

    /** 组行程总数 */
    private BigDecimal groupDistanceTotal;

    /** 组拜访总数 */
    private Integer groupVisitTotal;

    /** 组接待人总数 */
    private Integer groupReceiveTotal;

    /** 组总数 */
    private Integer groupTotal;
}
