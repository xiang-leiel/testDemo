package com.quantchi.tianji.service.search.interceptor;

import com.quantchi.tianji.service.search.dao.flow.WfOrderProjectMapper;
import org.snaker.engine.SnakerInterceptor;
import org.snaker.engine.core.Execution;

import javax.annotation.Resource;

/**
 * @Description 流程初审后置拦截处理类
 * @author leiel
 * @Date 2020/6/2 10:59 AM
 */

public class FirstAuditAfterInterceptor implements SnakerInterceptor {

    @Resource
    private WfOrderProjectMapper wfOrderProjectMapper;

    @Override
    public void intercept(Execution execution) {

        //获取流程关联的订单id
       String projectId = wfOrderProjectMapper.selectOrderId(execution.getOrder().getId());

        //修改关联项目id状态


        System.out.println("初审处理拦截器");

    }
}
