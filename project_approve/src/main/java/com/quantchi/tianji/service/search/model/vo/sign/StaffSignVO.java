package com.quantchi.tianji.service.search.model.vo.sign;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description 
 * @author leiel
 * @Date 2019/12/12 2:20 PM
 */
@Data
public class StaffSignVO {

    /** 签到总数 */
    private Integer staffSignTotal;

    /** 行程总数 */
    private BigDecimal staffDistanceTotal;

    /** 拜访总数 */
    private Integer staffVisitTotal;

    /** 接待人总数 */
    private Integer staffReceiveTotal;

    /** 用户信息 */
    private UserVO userVO;

}
