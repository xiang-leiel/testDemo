package com.quantchi.tianji.service.search.service.sign;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.model.vo.sign.VisitRecordParam;

/**
 * @author leiel
 * @Description 签到服务类
 * @Date 2019/12/9 9:06 PM
 */

public interface SignService {

    /**
     * 获取当前用户相关信息
     * @param staffId
     * @return
     */
    ResultInfo getUserInfo(String staffId);

    /**
     * 保存用户签到记录数据
     * @param staffId
     * @param visitLocation
     * @param visitAte
     * @param visitLte
     * @return
     */
    ResultInfo saveUserSignRecord(String staffId, Long visitId, String visitLocation, Double visitAte, Double visitLte);

    /**
     * 保存用户添加拜访记录数据
     * @param visitRecordParam
     * @return
     */
    ResultInfo addVisitRecord(VisitRecordParam visitRecordParam);

    /**
     * 获取用户未拜访的记录数据
     * @param staffId
     * @param locationAte
     * @param locatinLte
     * @return
     */
    ResultInfo getVisitRecord(String staffId, Double locationAte, Double locatinLte);


    /**
     * 根据时间获取当前所有组的签到情况
     * @param startTime
     * @param endTime
     * @return
     */
    ResultInfo getStatisticAll(String startTime, String endTime);

    /**
     * 根据时间获取当前所有组的签到情况
     * @param startTime
     * @param endTime
     * @return
     */
    ResultInfo getStatisticByGroup(String groupId, String startTime, String endTime, Boolean showType);


    /**
     * 根据时间段，获取当前组飞行数据
     * @param startTime
     * @param endTime
     * @return
     */
    ResultInfo getFlyDataByGroup(String straffId, String groupId, String startTime, String endTime, Boolean flag);

    /**
     * 根据时间段，获取某个人飞行数据
     * @param startTime
     * @param endTime
     * @return
     */
    ResultInfo getFlyDataByStaff(String staffId, String groupId, String startTime, String endTime);


    /**
     * 根据时间获取当前所有组的签到情况
     * @param startTime
     * @param endTime
     * @return
     */
    ResultInfo getStatisticByStaff(String staffId, String startTime, String endTime);

    /**
     * 获取当前组所有人员
     * @return
     */
    ResultInfo getStaffByGroup(String staffId, String groupId, Integer type);

    /**
     * 获取当前招商人一个月的情况
     * @param staffId
     * @param startTime
     * @param endTime
     * @return
     */
    ResultInfo getSignStationByStaff(String staffId, String startTime, String endTime);

    /**
     * 获取所有组的飞行数据
     * @param staffId
     * @param startTime
     * @param endTime
     * @return
     */
    ResultInfo getFlyAllGroup(String staffId, String startTime, String endTime);


    ResultInfo queryVisitRecord(String staffId);

    /**
     * 获取拜访数据
     * @param visitId
     * @return
     */
    ResultInfo queryVisitData(String visitId);


}
