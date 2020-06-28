package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.InvUserDetail;
import com.quantchi.tianji.service.search.model.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InvUserDetailMapper {
    int deleteByPrimaryKey(String userId);

    int insert(InvUserDetail record);

    int insertSelective(InvUserDetail record);

    InvUserDetail selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(InvUserDetail record);

    int updateByPrimaryKey(InvUserDetail record);

    UserInfo getUserInfoByStaffId(@Param("staffId") String staffId);

    /**
     * 获取所有的组别
     * @return
     */
    List<String> queryGroup();

    /**
     * 获取当前组别下的所有组员id
     * @return
     */
    List<String> queryStaffId(@Param("staffGroup") String staffGroup);

    /**
     * 获取当前组别下的所有组
     * @return
     */
    InvUserDetail queryStaffIdOne(@Param("staffGroup") String staffGroup);
}