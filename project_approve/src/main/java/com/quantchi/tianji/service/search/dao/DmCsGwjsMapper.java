package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.DmCsGwjs;

public interface DmCsGwjsMapper {
    int deleteByPrimaryKey(Integer gwjsDm);

    int insert(DmCsGwjs record);

    int insertSelective(DmCsGwjs record);

    DmCsGwjs selectByPrimaryKey(Integer gwjsDm);

    int updateByPrimaryKeySelective(DmCsGwjs record);

    int updateByPrimaryKey(DmCsGwjs record);
}