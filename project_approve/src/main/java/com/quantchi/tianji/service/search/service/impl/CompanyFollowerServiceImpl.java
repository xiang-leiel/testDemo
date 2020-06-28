package com.quantchi.tianji.service.search.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.config.AppConfig;
import com.quantchi.tianji.service.search.dao.CompanyFollowerDao;
import com.quantchi.tianji.service.search.dao.StatusLogDao;
import com.quantchi.tianji.service.search.dao.UserDao;
import com.quantchi.tianji.service.search.exception.TianjiErrorCode;
import com.quantchi.tianji.service.search.helper.ElasticsearchHelper;
import com.quantchi.tianji.service.search.helper.SearchUtil;
import com.quantchi.tianji.service.search.model.CompanyFollower;
import com.quantchi.tianji.service.search.model.StatusLog;
import com.quantchi.tianji.service.search.model.WorkTask;
import com.quantchi.tianji.service.search.service.CompanyFollowerService;
import com.quantchi.tianji.service.search.service.StatusLogService;
import com.quantchi.tianji.service.search.utils.ResultUtils;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author whuang
 * @date 2019/7/11
 */
@Service
public class CompanyFollowerServiceImpl implements CompanyFollowerService {

    @Autowired
    private CompanyFollowerDao companyFollowerDao;

    @Autowired
    private StatusLogService statusLogService;

    @Autowired
    private ElasticsearchHelper elasticsearchHelper;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private SearchUtil searchUtil;

    @Value("${hs.url}")
    private String hsUrl;

    @Override
    public ResultInfo save(CompanyFollower companyFollower) {
        try{
            //查询是否已存在
            CompanyFollower companyFollowerExists = companyFollowerDao.selectByStaffIdAndCompanyId(companyFollower.getStaffId(), companyFollower.getCompanyId());
            if(companyFollowerExists == null) {
                companyFollowerDao.insert(companyFollower);
            } else {
                companyFollowerExists.setStatus(companyFollower.getStatus());
                companyFollowerDao.updateByPrimaryKey(companyFollowerExists);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.fail(TianjiErrorCode.INSERT_ERROR);
        }
        return ResultUtils.success("success");
    }


    @Override
    public ResultInfo listFollowCompanys(String staffId, Integer page, Integer pageSize) {
        page = page == null ? 1 : page;
        pageSize = pageSize == null ? 10 : pageSize;
        PageHelper.startPage(page, pageSize);
        List<CompanyFollower> companyFollowers = userDao.listFollowCompanys(staffId);
        PageHelper.clearPage();
        PageInfo<CompanyFollower> pageInfo = new PageInfo<>(companyFollowers);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("total", pageInfo.getTotal());
        resultMap.put("pages", pageInfo.getPages());
        List<Map<String,Object>> list = new ArrayList<>();
        if (companyFollowers!=null && companyFollowers.size()>0) {
            List<Map<String, Object>> lzCompanyDetails = listLzCompanyDetails(companyFollowers);
            List<Map<String, Object>> hsCompanyDetailsByWeb = listHsCompanyDetailsByWeb(companyFollowers);
            List<Map<String, Object>> hsCompanyDetailsByApp = listHsCompanyDetailsByApp(companyFollowers);
            list.addAll(lzCompanyDetails);
            list.addAll(hsCompanyDetailsByWeb);
            list.addAll(hsCompanyDetailsByApp);
        }
        resultMap.put("list", list);
        return ResultUtils.success(resultMap);
    }

    public List<Map<String,Object>> listLzCompanyDetails(List<CompanyFollower> companyFollowers) {

        if (companyFollowers == null||companyFollowers.size() <= 0) {
            return new ArrayList<>();
        }
        //获取来自量知的工作任务
        List<String> lzIds = companyFollowers.stream()
                .filter(companyFollower -> {
                    return companyFollower.getFrom().equals("量知");
                })
                .map((companyFollower) -> (companyFollower.getCompanyId()))
                .collect(Collectors.toList());

        //获取来自量知的工作任务的公司详情
        List<Map<String, Object>> lzCompanyDetails = getLzCompanyDetails(lzIds);
        return lzCompanyDetails;
    }

    private List<Map<String, Object>> getLzCompanyDetails(List<String> lzCompanyIds) {
        if (lzCompanyIds==null||lzCompanyIds.size() <= 0) {
            return new ArrayList<>();
        }
        String []ids = new String[lzCompanyIds.size()];
        ids  = lzCompanyIds.toArray(ids);
        MultiGetResponse response = elasticsearchHelper.multiGet(appConfig.getEsIndexCompany(), appConfig.getEsTypeCompany(), ids);
        MultiGetItemResponse[] responses = response.getResponses();
        List<Map<String,Object>> list = new ArrayList<>();
        for (MultiGetItemResponse resp:responses) {
            Map<String, Object> sourceAsMap = resp.getResponse().getSourceAsMap();
            if (sourceAsMap!=null) {
                sourceAsMap.put("id",resp.getId());
            }
            list.add(sourceAsMap);
        }
        return list;
    }

    public List<Map<String,Object>> listHsCompanyDetailsByWeb(List<CompanyFollower> companyFollowers) {

        if (companyFollowers == null||companyFollowers.size() <= 0) {
            return new ArrayList<>();
        }
        //获取来自量知的工作任务
        List<String> hsIds = companyFollowers.stream()
                .filter(companyFollower -> {
                    return companyFollower.getFrom().equals("火石")&&companyFollower.getOrigin().equals("web");
                })
                .map((companyFollower) -> (companyFollower.getCompanyId()))
                .collect(Collectors.toList());

        //获取来自量知的工作任务的公司详情
        List<Map<String, Object>> hsCompanyDetails = searchUtil.listHsCompanyDetails(hsIds);
        return hsCompanyDetails;
    }

    public List<Map<String,Object>> listHsCompanyDetailsByApp(List<CompanyFollower> companyFollowers) {

        if (companyFollowers == null||companyFollowers.size() <= 0) {
            return new ArrayList<>();
        }
        //获取来自量知的工作任务
        List<String> hsIds = companyFollowers.stream()
                .filter(companyFollower -> {
                    return companyFollower.getFrom().equals("火石")&&companyFollower.getOrigin().equals("app");
                })
                .map((companyFollower) -> (companyFollower.getCompanyId()))
                .collect(Collectors.toList());

        //获取来自量知的工作任务的公司详情
        List<Map<String, Object>> hsCompanyDetails = searchUtil.listHsCompanyDetailsByApp(hsIds);
        return hsCompanyDetails;
    }

}
