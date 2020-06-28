package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.UseAccessRecord;

public interface UseAccessRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UseAccessRecord record);

    int insertSelective(UseAccessRecord record);

    UseAccessRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UseAccessRecord record);

    int updateByPrimaryKey(UseAccessRecord record);
}