package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.DmCsTzgm;
import com.quantchi.tianji.service.search.model.XmGlTzgm;

import java.util.List;

public interface DmCsTzgmMapper {
    int deleteByPrimaryKey(Integer tzgmDm);

    int insert(DmCsTzgm record);

    int insertSelective(DmCsTzgm record);

    DmCsTzgm selectByPrimaryKey(Integer tzgmDm);

    int updateByPrimaryKeySelective(DmCsTzgm record);

    int updateByPrimaryKey(DmCsTzgm record);

    List<DmCsTzgm> selectTzgmAll();
}