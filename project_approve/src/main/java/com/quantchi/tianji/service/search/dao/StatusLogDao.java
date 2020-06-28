package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.StatusLog;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface StatusLogDao {

    int deleteByPrimaryKey(Long id);

    int insert(StatusLog record);

    int insertSelective(StatusLog record);

    StatusLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StatusLog record);

    int updateByPrimaryKey(StatusLog record);

    StatusLog selectLastByStaffIdAndCompanyId(@Param("staffId") String staffId, @Param("companyId") String companyId);

    List<StatusLog> listByStaffIdAndCompanyId(@Param("staffId") String staffId, @Param("companyId") String companyId);

    List<StatusLog> listMyLoggedCompany(@Param("staffId")String staffId);

    List<StatusLog> list5LastByCompanyIdAndField(@Param("companyId")String companyId,@Param("field")String field);

    List<Map<String,Object>> recordRand(@Param("startTime") Date startTime);

    StatusLog getLast(@Param("staffId") String staffId, @Param("companyId") String companyId);

    List<StatusLog> listStatusLogByCompanyId(@Param("companyId") String companyId,
                                             @Param("messageTime") Date messageTime);

    List<String> listStatusLogByCompanyIds(@Param("list") List<String> list, @Param("messageTime") Date messageTime);

    StatusLog getLastestLogByCompanyId(@Param("companyId") String companyId);

    StatusLog getNewestLogByCompanyId(String companyId);

}