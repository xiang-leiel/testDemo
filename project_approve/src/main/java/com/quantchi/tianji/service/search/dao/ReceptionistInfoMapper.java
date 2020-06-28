package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.ReceptionistInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReceptionistInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ReceptionistInfo record);

    int insertSelective(ReceptionistInfo record);

    ReceptionistInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ReceptionistInfo record);

    int updateByPrimaryKey(ReceptionistInfo record);

    List<ReceptionistInfo> selectByVisitId(@Param("visitId")Long visitId);

    ReceptionistInfo selectByName(@Param("name")String name, @Param("visitId")Long visitId);

    Integer coutByStaffId(@Param("staffId")String staffId);

    Long insertAndReturnId(ReceptionistInfo record);
}