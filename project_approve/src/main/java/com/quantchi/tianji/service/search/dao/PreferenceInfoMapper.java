package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.PreferenceInfo;
import org.apache.ibatis.annotations.Param;

public interface PreferenceInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PreferenceInfo record);

    int insertSelective(PreferenceInfo record);

    PreferenceInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PreferenceInfo record);

    int updateByPrimaryKey(PreferenceInfo record);

    PreferenceInfo fetchOne(@Param("staffId")String staffId);
}