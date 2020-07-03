package com.quantchi.tianji.service.search.interfaces.http;

import com.quantchi.tianji.service.search.utils.SnakerEngineFacets;
import org.snaker.engine.core.OrderService;
import org.snaker.engine.core.ProcessService;
import org.snaker.engine.entity.Order;
import org.snaker.engine.entity.Process;
import org.snaker.engine.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 
 * @author leiel
 * @Date 2020/6/10 10:44 AM
 */
@RestController
@RequestMapping(value = "/demo/")
public class DemoController {

    @Autowired
    private SnakerEngineFacets snakerEngineFacets;

    @Autowired
    private ProcessService processService;

    @Autowired
    private OrderService orderService;

    /**
     * 加载.snaker文件 获取流程id 一个项目加载一次就可以
     * @return
     */
    @RequestMapping(value = "/load", method= RequestMethod.GET)
    public String loadFile() {

        String s = snakerEngineFacets.initFlows();
        System.out.println(s);
        return s;
    }

    /**
     * 获取流程
     */
    @RequestMapping(value = "/getProcess", method= RequestMethod.GET)
    public void getProcess() {
        String id = "013835db6ff84046b1254c079a6be5bf";
        Process process = processService.getProcessById(id);
        System.out.println(process);
    }

    /**
     * 创建流程实例
     */
    @RequestMapping(value = "/produceOrderId", method= RequestMethod.GET)
    public Order produceOrderId() {
        String processId = "013835db6ff84046b1254c079a6be5bf";

        Process process = processService.getProcessById(processId);

        //当前用户的id  也可设置角色，如果该处设置角色，则所有具有同一角色的用户都可以看到同一条数据
        String operatorRole = "1002159";

        //初审用户的id
        String firstAuditRole = "1002022";

        //终审用户的id
        String endAuditRole = "1002027";

        //封装参数测试
        Map<String, Object> params = new HashMap<>();
        params.put("report.operator", operatorRole);
        params.put("reason", "测试");
        params.put("firstAudit.operator", firstAuditRole);
        params.put("endAudit.operator", endAuditRole);
        Order test = orderService.createOrder(process, operatorRole, params);
        System.out.println(test);
        return test;
    }

    /**
     * 测试执行任务按processId --- 流程发起，流程实例化。确定流程参与者
     */
    @RequestMapping(value = "/executeTaskByProcessId", method= RequestMethod.GET)
    public void executeTaskByProcessId() {

        String processId = "013835db6ff84046b1254c079a6be5bf";

        //当前用户的id  也可设置角色，如果该处设置角色，则所有具有同一角色的用户都可以看到同一条数据
        String operatorRole = "1002159";

        //初审用户的id
        String firstAuditRole = "1002022";

        //终审用户的id
        String endAuditRole = "1002027";

        //封装参数测试
        Map<String, Object> params = new HashMap<>();
        params.put("report.operator", operatorRole);
        params.put("reason", "测试");
        params.put("firstAudit.operator", firstAuditRole);
        params.put("endAudit.operator", endAuditRole);

        Order test = snakerEngineFacets.startAndExecute(processId, operatorRole, params);
        System.out.println(test);

    }

    /**
     * 测试执行任务按processId ---执行流程任务终审
     */
    @RequestMapping(value = "/executeTaskDeptAudit", method= RequestMethod.GET)
    public void executeTaskDeptAudit() {

        String taskId = "ab75053c767046aeb449871207f68adc";

        //封装参数测试
        Map<String, Object> params = new HashMap<>();

        //endAudit.operator 为.snaker文件中的assignee属性
        params.put("endAudit.operator","1002027");

        //审核原因
        params.put("reason","同意");

        //operator为当前审核用户
        List<Task> test = snakerEngineFacets.execute(taskId, "1002027", params);

        System.out.println(test);

    }

}
