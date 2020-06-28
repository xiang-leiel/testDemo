package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.VisitLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VisitLogDao {
    int deleteByPrimaryKey(Long id);

    int insert(VisitLog record);

    int insertSelective(VisitLog record);

    VisitLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VisitLog record);

    int updateByPrimaryKey(VisitLog record);

    List<VisitLog> list5LastByCompanyIdAndField(@Param("companyId") String companyId, @Param("field") String field);

    VisitLog getLast(@Param("staffId") String staffId, @Param("companyId") String companyId);
}