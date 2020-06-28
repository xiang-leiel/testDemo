package com.quantchi.tianji.service.search.interfaces.http;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.model.Order;
import com.quantchi.tianji.service.search.model.WorkCircleImpl;
import com.quantchi.tianji.service.search.service.StatusLogService;
import com.quantchi.tianji.service.search.service.WorkCircleService;
import com.quantchi.tianji.service.search.utils.ResultUtils;
import org.h2.value.ValueStringIgnoreCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: DeQing-InvestmentEnterprise
 * @description: 发现
 * @author: mf
 * @create: 2019-07-10 19:38
 **/
@RestController
@RequestMapping("/explore")
public class ExploreController {

    @Autowired
    private StatusLogService statusLogService;

    @Autowired
    private WorkCircleService workCircleService;

    @GetMapping(value = "/workcircle/list", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResultInfo<Map<String,Object>> listWorkCircles(String sortBy, String sortOrder, Integer page, Integer pageSize) {
        Order order = new Order(sortBy,sortOrder,page,pageSize);
        Map<String, Object> map = workCircleService.list(order);
       return ResultUtils.success(map);
    }

    @GetMapping(value = "/recordrank/list",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResultInfo recordRank(String time) {
        List<Map<String, Object>> maps = statusLogService.recordRank(time);
        return ResultUtils.success(maps);
    }


}
