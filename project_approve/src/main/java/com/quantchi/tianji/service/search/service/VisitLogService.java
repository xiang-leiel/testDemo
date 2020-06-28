package com.quantchi.tianji.service.search.service;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.model.VisitLog;

/**
 * @author whuang
 * @date 2019/7/12
 */
public interface VisitLogService {

    ResultInfo save(VisitLog visitLog);
    ResultInfo get5LastStatusLogByCompanyId4EveryField(String companyId);
    ResultInfo getLast(String staffId,String companyId);
}
