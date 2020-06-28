package com.quantchi.tianji.service.search.service;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.model.CompanyFollower;

import java.util.List;

/**
 * @author whuang
 * @date 2019/7/11
 */
public interface CompanyFollowerService {

    ResultInfo save(CompanyFollower companyFollower);

    ResultInfo listFollowCompanys(String staffId, Integer page, Integer pageSize);
}
