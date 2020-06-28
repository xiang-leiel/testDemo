package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.CompanyFollower;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompanyFollowerDao {
    int deleteByPrimaryKey(Long id);

    int insert(CompanyFollower record);

    int insertSelective(CompanyFollower record);

    CompanyFollower selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CompanyFollower record);

    int updateByPrimaryKey(CompanyFollower record);

    CompanyFollower selectByStaffIdAndCompanyId(@Param("staffId") String staffId, @Param("companyId") String companyId);

    List<CompanyFollower> listMyFollowedCompany(@Param("staffId") String staffId);
}