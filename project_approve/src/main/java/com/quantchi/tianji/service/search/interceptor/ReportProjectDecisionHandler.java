package com.quantchi.tianji.service.search.interceptor;

import org.snaker.engine.DecisionHandler;
import org.snaker.engine.core.Execution;

/**
 * @Description 项目上报决策拦截处理类
 * @author leiel
 * @Date 2020/6/2 4:45 PM
 */
public class ReportProjectDecisionHandler implements DecisionHandler {

    /**
     * 返回的结果 就是下一个节点
     * @param execution
     * @return
     */
    @Override
    public String decide(Execution execution) {

        String userId = execution.getOperator();

        //获取当前操作员的部门
        System.out.println("测试决策拦截器");

        //如果不属于某个部门则跳转到终审 endAudit
        if(true) {
            return "endAudit";
        }

        //否则跳转到初审 firstAudit
        return "firstAudit";
    }
}
