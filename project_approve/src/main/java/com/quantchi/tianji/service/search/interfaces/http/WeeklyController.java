package com.quantchi.tianji.service.search.interfaces.http;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.model.PageBean;
import com.quantchi.tianji.service.search.service.WeeklyService;
import com.quantchi.tianji.service.search.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/weekly")
public class WeeklyController {
    @Autowired
    private WeeklyService weeklyService;
    @GetMapping("/getWeeklyNumInfo")
    public ResultInfo getWeeklyNumInfo(String staffId){
        Map<String, Object> result = weeklyService.getWeeklyNumInfo(staffId);
        return ResultUtils.success(result);
    }

    @GetMapping("/getAllNumInfo")
    public ResultInfo getAllNumInfo(String staffId){
        Map<String, Object> result = weeklyService.getAllNumInfo(staffId);
        return ResultUtils.success(result);
    }

    @GetMapping("/getWeeklyHistoryNum")
    public ResultInfo getWeeklyHistoryNum(String staffId, Integer from, Integer size){
        PageBean<Map<String, Object>> result = weeklyService.getWeeklyHistoryNum(staffId, from, size);
        return ResultUtils.success(result);
    }
}
