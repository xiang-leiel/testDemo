package com.quantchi.tianji.service.search.service;

import com.quantchi.tianji.service.search.model.Order;
import com.quantchi.tianji.service.search.model.WorkCircle;

import java.util.Map;

/**
 * @program: DeQing-InvestmentEnterprise
 * @description: 用于查找,记录工作圈
 * @author: mf
 * @create: 2019-07-10 15:33
 **/
public interface WorkCircleService {
    /**
     * 保存工作圈
     * @param workCircle 工作圈实体
     */
    void save(WorkCircle workCircle);

    /**
     * 查找所有工作圈
     * @return 工作圈列表
     * @param order
     */
    Map<String, Object> list(Order order);
}
