package com.quantchi.tianji.service.search.interceptor;

import org.snaker.engine.DecisionHandler;
import org.snaker.engine.core.Execution;

/**
 * @Description 项目初审决策拦截处理类
 * @author leiel
 * @Date 2020/6/30 2:15 PM
 */

public class FirstAuditDescisionHandler implements DecisionHandler {

    @Override
    public String decide(Execution execution) {

        System.out.println("初审拦截器");

        //如果审批通过则去终审 否则结束
        if(true) {
            return "endAudit";
        }else {
            return "end";
        }

    }

}
