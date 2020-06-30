package com.quantchi.tianji.service.search.interceptor;

import org.snaker.engine.SnakerInterceptor;
import org.snaker.engine.core.Execution;

/**
 * @Description demo流程上报后置拦截处理类
 * @author leiel
 * @Date 2020/6/2 5:01 PM
 */

public class ReportAfterInterceptor implements SnakerInterceptor {

    @Override
    public void intercept(Execution execution) {

        System.out.println("上报项目处理拦截器");

    }
}
