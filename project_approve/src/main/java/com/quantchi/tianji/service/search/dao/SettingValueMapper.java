package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.SettingValue;
import org.apache.ibatis.annotations.Param;

public interface SettingValueMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SettingValue record);

    int insertSelective(SettingValue record);

    SettingValue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SettingValue record);

    int updateByPrimaryKey(SettingValue record);

    SettingValue queryByGroup(@Param("group")String group);

    SettingValue queryByType(@Param("type") String type);
}