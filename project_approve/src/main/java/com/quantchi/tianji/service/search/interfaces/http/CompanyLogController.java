package com.quantchi.tianji.service.search.interfaces.http;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.dao.WorkTaskDao;
import com.quantchi.tianji.service.search.model.StatusLog;
import com.quantchi.tianji.service.search.model.VisitLog;
import com.quantchi.tianji.service.search.model.WorkTask;
import com.quantchi.tianji.service.search.service.StatusLogService;
import com.quantchi.tianji.service.search.service.VisitLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**企业记录
 * @author whuang
 * @date 2019/7/11
 */
@RestController
@RequestMapping(value = "/index/company")
public class CompanyLogController {

    @Autowired
    private StatusLogService statusLogService;

    @Autowired
    private VisitLogService visitLogService;

    @PostMapping(value = "/statusLog",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public String statusLog(@RequestBody StatusLog statusLog) {
        ResultInfo resultInfo = statusLogService.save(statusLog);
        return JSONObject.toJSONString(resultInfo, SerializerFeature.WriteMapNullValue);
    }

    @PostMapping(value = "/visitLog",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public String visitLog(@RequestBody VisitLog visitLog) {
        ResultInfo resultInfo = visitLogService.save(visitLog);
        return JSONObject.toJSONString(resultInfo, SerializerFeature.WriteMapNullValue);
    }

    @GetMapping(value = "/statusLogHistory", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public String statusLogHistory(String companyId) {
        ResultInfo resultInfo = statusLogService.get5LastStatusLogByCompanyId4EveryField(companyId);
        return JSONObject.toJSONString(resultInfo, SerializerFeature.WriteMapNullValue);
    }

    @GetMapping(value = "/visitLogHistory",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public String visitLogHistory(String companyId) {
        ResultInfo resultInfo = visitLogService.get5LastStatusLogByCompanyId4EveryField(companyId);
        return JSONObject.toJSONString(resultInfo, SerializerFeature.WriteMapNullValue);
    }


    @GetMapping(value = "/lastVisitLog",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public String lastVisitLog(String companyId) {
        ResultInfo resultInfo = visitLogService.getLast(null, companyId);
        return JSONObject.toJSONString(resultInfo, SerializerFeature.WriteMapNullValue);
    }

    @GetMapping(value = "/lastStatusLog",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public String lastStatusLog(String companyId) {
        ResultInfo resultInfo = statusLogService.getLast(null, companyId);
        return JSONObject.toJSONString(resultInfo, SerializerFeature.WriteMapNullValue);
    }
}
