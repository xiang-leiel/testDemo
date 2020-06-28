package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.DmCsYhyc;

import java.util.List;

public interface DmCsYhycMapper {
    int deleteByPrimaryKey(Integer yhycDm);

    int insert(DmCsYhyc record);

    int insertSelective(DmCsYhyc record);

    DmCsYhyc selectByPrimaryKey(Integer yhycDm);

    int updateByPrimaryKeySelective(DmCsYhyc record);

    int updateByPrimaryKey(DmCsYhyc record);

    List<DmCsYhyc> selectYclyAll(int type);
}