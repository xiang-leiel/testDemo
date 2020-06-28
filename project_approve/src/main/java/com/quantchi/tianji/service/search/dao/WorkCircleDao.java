package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.Order;
import com.quantchi.tianji.service.search.model.WorkCircle;
import com.quantchi.tianji.service.search.model.WorkCircleImpl;

import java.util.List;

/**
 * @program: DeQing-InvestmentEnterprise
 * @author: mf
 * @create: 2019-07-10 15:44
 **/
public interface WorkCircleDao {

    /**
     * 插入一条工作圈记录
     * @param workCircle 工作圈实体
     */
    void insert(WorkCircle workCircle);

    /**
     * 获取来源于工作指派的工作圈列表
     * @return 返回工作圈列表
     */
    List<WorkCircleImpl> listWorkCirclesFromTaskByTime(Order order);

    /**
     * 获取来源于记录的工作圈列表
     * @return 返回工作圈列表
     */
    List<WorkCircleImpl> listWorkCirclesFromTaskByStaffId(Order order);

    List<WorkCircleImpl> listWorkCirclesFromTaskByDepartId(Order order);

    List<WorkCircleImpl> listWorkCirclesFromRecordByTime(Order order);

    List<WorkCircleImpl> listWorkCirclesFromRecordByStaffId(Order order);

    List<WorkCircleImpl> listWorkCirclesFromRecordByDepartId(Order order);

    /**
     * 获取工作圈记录总数
     * @return
     */
    Integer getTotalCount();

    Integer countWorkCirclesFromTaskByStaffId(Order order);

    Integer countWorkCirclesFromRecordByStaffId(Order order);

    Integer countWorkCirclesFromRecordByDepartId(Order order);

    Integer countWorkCirclesFromTaskByDepartId(Order order);
}
