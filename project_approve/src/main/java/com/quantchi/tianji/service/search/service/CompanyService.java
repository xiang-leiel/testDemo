package com.quantchi.tianji.service.search.service;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.model.Company;

import java.util.Map;

/**
 * @author whuang
 * @date 2019/7/15
 */
public interface CompanyService {

    ResultInfo getCompanyDetail(String staffId, String companyId, String from);

    ResultInfo search(Company company);

    ResultInfo listCities();

    ResultInfo listCompanysNearBy(String industry,String keyword, Double latitude, Double longitude, String distance, Integer from,
                                  Integer size);
    ResultInfo index(Integer pageSize);

    Map<String,Object> getCompanyDetailRelation(String companyId);
}
