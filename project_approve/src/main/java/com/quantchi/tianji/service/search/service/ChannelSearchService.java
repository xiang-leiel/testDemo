package com.quantchi.tianji.service.search.service;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.model.Channel;

import java.util.Map;

public interface ChannelSearchService {
    /**
     * 获取渠道首页内容
     * @return
     * @param sort
     */
    ResultInfo index(String sort);

    ResultInfo search(Channel channel);

    ResultInfo normalChannelMessage(String staffId, Integer page, Integer pageSize);

    ResultInfo leadChannelMessage(String staffId, Integer page, Integer pageSize);

    ResultInfo listMeetingInfoByCondition(Map<String,String> conditionMap);
}
