package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.DmCsGj;

import java.util.List;

public interface DmCsGjMapper {
    int deleteByPrimaryKey(Integer gjDm);

    int insert(DmCsGj record);

    int insertSelective(DmCsGj record);

    DmCsGj selectByPrimaryKey(Integer gjDm);

    int updateByPrimaryKeySelective(DmCsGj record);

    int updateByPrimaryKey(DmCsGj record);

    List<DmCsGj> getNation();
}