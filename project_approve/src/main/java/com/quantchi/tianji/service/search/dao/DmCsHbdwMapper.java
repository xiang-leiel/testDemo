package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.DmCsHbdw;

import java.util.List;

public interface DmCsHbdwMapper {
    int deleteByPrimaryKey(Integer hbdwDm);

    int insert(DmCsHbdw record);

    int insertSelective(DmCsHbdw record);

    DmCsHbdw selectByPrimaryKey(Integer hbdwDm);

    int updateByPrimaryKeySelective(DmCsHbdw record);

    int updateByPrimaryKey(DmCsHbdw record);

    List<DmCsHbdw> selectAll();
}