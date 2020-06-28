package com.quantchi.tianji.service.search.service;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.model.Event;

/**
 * 企业资讯接口
 */
public interface EventService {
    /**
     * 资讯首页接口
     * @param pageSize
     * @return
     */
    ResultInfo index(Integer pageSize);

    /**
     * 资讯搜索
     * @param company
     * @return
     */
    ResultInfo search(Event company);

    /**
     * 返回企业新闻资讯
     * @param name
     * @return
     */
    ResultInfo listNewsByCompanyName(String name, Integer page, Integer pageSize);

    ResultInfo listAllEvents(Event event);

    ResultInfo listNewNormalEventsFromLz(String staffId, Integer page, Integer pageSize);

    ResultInfo listNewLeadEventsFromLz(String staffId, Integer page, Integer pageSize);

    ResultInfo listNewNormalEventsFromHs(String staffId, Integer page, Integer pageSize);

    ResultInfo listNewLeadEventsFromHs(String staffId, Integer page, Integer pageSize);
}
