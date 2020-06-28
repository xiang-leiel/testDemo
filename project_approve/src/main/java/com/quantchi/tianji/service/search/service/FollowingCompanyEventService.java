package com.quantchi.tianji.service.search.service;

import com.quantchi.core.message.ResultInfo;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.stereotype.Service;

/**
 * @author whuang
 * @date 2019/7/13
 */
public interface FollowingCompanyEventService {

    ResultInfo listFollowingCompanyEvent(String staffId,String keyword,Integer page, Integer pageSize);

}
