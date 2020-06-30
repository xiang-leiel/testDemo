package com.quantchi.tianji.service.search.interceptor;

import org.snaker.engine.SnakerInterceptor;
import org.snaker.engine.core.Execution;

/**
 * @Description 流程终审后置拦截处理类
 * @author leiel
 * @Date 2020/6/8 2:18 PM
 */

public class EndAuditAfterInterceptor implements SnakerInterceptor {

    @Override
    public void intercept(Execution execution) {

        System.out.println("终审处理拦截器");

    }
}
