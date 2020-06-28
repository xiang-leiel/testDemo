package com.quantchi.tianji.service.search.interfaces.http;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.service.DockingService;
import com.quantchi.tianji.service.search.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/dock")
public class DockingController {
    @Autowired
    private DockingService dockingService;

    @GetMapping("/getMedicineInfoList")
    public ResultInfo getMedicineInfoList(String keyword, String area, String industry, String companyIds, String page, String pageSize) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("keyword", keyword);
        paramMap.put("area", area);
        paramMap.put("industry", industry);
        paramMap.put("companyIds", companyIds);
        paramMap.put("current", page);
        paramMap.put("pageSize", pageSize);
        Map<String, Object> result = dockingService.getMedicineInfoList(paramMap);
        return ResultUtils.success(result);
    }

    /**
     * 获取企业信息列表
     * @param condition
     * @return
     */
    @PostMapping("/getMedicineCompanyInfoList")
    public ResultInfo getMedicineCompanyInfoList(@RequestBody Map<String, Object> condition) {
        Map<String, Object> result = dockingService.getMedicineCompanyInfoList(condition);
        return ResultUtils.success(result);
    }

    @GetMapping("/getMedicineDetailInfo")
    public ResultInfo getMedicineDetailInfo(String id) {
        Map<String, Object> result = dockingService.getMedicineDetailInfo(id);
        return ResultUtils.success(result);
    }
    @GetMapping("/getMedicineCompanyDetail")
    public ResultInfo getMedicineCompanyDetail(String id) {
        Map<String, Object> result = dockingService.getMedicineCompanyDetail(id);
        return ResultUtils.success(result);
    }
    @CrossOrigin
    @GetMapping("/getNearCompanyList")
    public ResultInfo getNearCompanyList(String latitude, String longitude, String distance,String keyword, String page, String pageSize) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("lat", latitude);
        paramMap.put("lng", longitude);
        paramMap.put("distance", distance);
        paramMap.put("current", page);
        paramMap.put("pageSize", pageSize);
        paramMap.put("keyword", keyword);
        Map<String, Object> result = dockingService.getNearCompanyList(paramMap);
        return ResultUtils.success(result);
    }
    @PostMapping("/getMeetingList")
    public ResultInfo getMeetingList(@RequestBody Map<String, Object> condition) {
        Map<String, Object> result = dockingService.getMeetingList(condition);
        return ResultUtils.success(result);
    }

}
