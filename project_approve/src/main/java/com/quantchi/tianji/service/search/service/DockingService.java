package com.quantchi.tianji.service.search.service;

import java.util.Map;

public interface DockingService {
    Map<String,Object> getMedicineInfoList(Map<String,Object> paramMap);

    Map<String,Object> getMedicineCompanyInfoList(java.util.Map<java.lang.String,java.lang.Object> condition);

    Map<String,Object> getMedicineDetailInfo(String id);

    Map<String,Object> getMedicineCompanyDetail(String id);

    Map<String,Object> getNearCompanyList(Map<String,Object> paramMap);

    Map<String,Object> getMeetingList(Map<String,Object> condition);
}
