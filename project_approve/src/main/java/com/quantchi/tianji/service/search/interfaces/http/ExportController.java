package com.quantchi.tianji.service.search.interfaces.http;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.model.PageBean;
import com.quantchi.tianji.service.search.service.ExportService;
import com.quantchi.tianji.service.search.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/export")
public class ExportController {
    @Autowired
    private ExportService exportService;
    @GetMapping("/exportInfo")
    public void exportInfo(Integer model, String group, String startDate, String endDate, String email, String staffId){
        Map<String, Object> condition = new HashMap<>();
        String[] groupArry = group.split(",");
        condition.put("groupList", groupArry);
        condition.put("staffId", staffId);
        condition.put("startDate", startDate);
        condition.put("endDate", endDate);
        System.out.println("condition:"+condition);
        System.out.println("model:"+model);
        System.out.println("date:"+startDate+",,,,,"+endDate);
        switch (model){
            case 0: // 招商项目信息
                exportService.getProjectInfo(condition, email);
                break;
            case 1: //月度绩效
                exportService.getPerformanceInfo(condition, email);
                break;
            case  2: //引资引才情况
                exportService.getImportInfo(condition, email);
                break;
            case 3:
                exportService.getVisitInfo(condition, email);
                break;
        }
        exportService.addExportHistory(staffId, model);
    }
    @GetMapping("/getExportGroupList")
    public ResultInfo getExportGroupList(){
        List<Map<String, String>> list = exportService.getExportGroupList();
        return ResultUtils.success(list);
    }
    @GetMapping("/getExportHistory")
    public ResultInfo getExportHistory(String userId, Integer from , Integer size){
        PageBean<Map<String, Object>> list = exportService.getExportHistory(userId, from, size);
        return ResultUtils.success(list);
    }
}
