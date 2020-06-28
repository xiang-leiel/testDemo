package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.DmCsUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DmCsUserMapper {
    int deleteByPrimaryKey(Integer userDm);

    int insert(DmCsUser record);

    int insertSelective(DmCsUser record);

    DmCsUser selectByPrimaryKey(Integer userDm);

    int updateByPrimaryKeySelective(DmCsUser record);

    int updateByPrimaryKey(DmCsUser record);

    /**
     * 根据手机号获取人员信息
     * @param mobile
     * @return
     */
    DmCsUser selectBymobile(@Param("mobile") String mobile);

    List<Integer> getJobByMobile(@Param("mobile") String mobile);

    Integer getDeptDmByUserId(Integer userId);

    List<String> selectListByDeptDm(Integer deptId);

    String selectNameByDingId(String staffId);

    Integer getUserDmByStaffId(String staffId);

    List<DmCsUser> selectByDeptDm(Integer deptId);

    String selectNameByUserDm(Integer userId);
}