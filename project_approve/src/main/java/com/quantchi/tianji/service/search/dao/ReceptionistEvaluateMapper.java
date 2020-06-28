package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.ReceptionistEvaluate;
import org.apache.ibatis.annotations.Param;

public interface ReceptionistEvaluateMapper {
    int insert(ReceptionistEvaluate record);

    int insertSelective(ReceptionistEvaluate record);

    ReceptionistEvaluate selectById(@Param("id")Long id);
}