package com.quantchi.tianji.service.search.interfaces.http;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.model.Company;
import com.quantchi.tianji.service.search.service.CompanyService;
import com.quantchi.tianji.service.search.service.WorkTaskService;
import com.quantchi.tianji.service.search.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 企业数据搜索
 * @author:whuang
 * @date: 2019.1.16
 */
@RestController
@RequestMapping("/company")
@Slf4j
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private WorkTaskService workTaskService;

    @GetMapping("/index")
    public ResultInfo index(Integer pageSize) {
        ResultInfo resultInfo = companyService.index(pageSize);
        return resultInfo;
    }

    @PostMapping(value = "/search")
    public String search(@RequestBody Company company) {
        ResultInfo resultInfo = companyService.search(company);
        return JSONObject.toJSONString(resultInfo, SerializerFeature.WriteMapNullValue);
    }


    @GetMapping(value = "/listCities",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public String listCities() {
        ResultInfo resultInfo = companyService.listCities();
        return JSONObject.toJSONString(resultInfo, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 查找给定地点附近的公司
     * @return
     */
    @CrossOrigin
    @GetMapping(value = "/listCompanysNearBy")
    public String listCompanysNearBy(String industry,String keyword, Double latitude, Double longitude, String distance, Integer page,
                                     Integer size) {
        log.info("lati:{},longt:{},distance:{}", latitude,longitude,distance);
        ResultInfo resultInfo = companyService.listCompanysNearBy(industry,keyword,latitude, longitude, distance, page, size);
        return JSONObject.toJSONString(resultInfo, SerializerFeature.WriteMapNullValue);
    }

    @GetMapping(value = "/detail",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public String getCompanyDetail(String staffId,String companyId ,String from) {

        //查询插入用户是否关注企业,from:区分数据来源,火石或量知
        ResultInfo companyInfo = companyService.getCompanyDetail(staffId, companyId, from);
        return JSONObject.toJSONString(companyInfo, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 查找给定地点附近的公司
     * @return
     */
    @GetMapping(value = "/isRecordable")
    public ResultInfo isRecordable(String companyId) {
        String staffId = workTaskService.isRecordable(companyId);
        return ResultUtils.success(staffId);
    }
    @GetMapping(value = "/getCompanyDetailRelation")
    public ResultInfo getCompanyDetailRelation(String companyId){
        Map<String, Object> result = companyService.getCompanyDetailRelation(companyId);
        return ResultUtils.success(result);
    }


}
