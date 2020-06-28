package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.CompanyFollower;
import com.quantchi.tianji.service.search.model.User;
import com.quantchi.tianji.service.search.model.UserDetail;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface UserDao {
    /**
     * 批量插入用户
     * @param allUsers 用户集合
     */
    void insertList(@Param("allUsers") List<User> allUsers);

    /**
     * 删除所有原来的用户数据
     */
    void deleteAll();

    List<String> getUserName(String id);

    /**
     * 在原有用户数据基础上更新用户数据
     * @param distinctUsers
     */
    void updateAllUser(List<User> distinctUsers);

    List<User> listUsers(User user);

    User getUserInfo(String userId);

    List<CompanyFollower> listFollowCompanys(@Param("staffId") String staffId);


    void saveUserLocation(@Param("staffId") String staffId, @Param("latitude") Double latitude,
                          @Param("longitude") Double longitude);

    void followCompany(@Param("userId") String userId, @Param("companyId") String companyId,
                       @Param("from") String from, @Param("origin") String origin);

    void unFollowCompany(@Param("userId") String userId, @Param("companyId") String companyId);


    List<String> listCompanysFollowed(@Param("staffId") String staffId);

    UserDetail getUserDetail(String staffId);

    UserDetail getUserDetailByMobile(@Param("mobile") String mobile);

    void updateTime(UserDetail userDetail);

    List<String> listCompanysFollowedByTime(@Param("staffId") String staffId, @Param("channelTime") Date channelTime);

    /**
     * cardId:招商员申请名片时，领导的apply_card_num添加
     * @param condition
     */
    void updateUserFollowCompanyCardNum(Map<String, Object> condition);

    void updateUserUploadLeaderCardNum(Map<String, Object> condition);

    void insertUserBasicDetail(Map<String, Object> userDetail);

    UserDetail getUserBasicDetail(String userId);

    void insertUserEducationInfo(Map<String,Object> condition);

    void insertUserWorkInfo(Map<String,Object> condition);

    void insertUserCityInfo(Map<String,Object> condition);

    void insertUserBusinessInfo(Map<String,Object> condition);

    void updateUserEducationInfo(Map<String,Object> condition);

    void updateUserWorkInfo(Map<String,Object> condition);

    void updateUserCityInfo(Map<String,Object> condition);

    void updateUserBusinessInfo(Map<String,Object> condition);

    void deleteUserEducationById(int id);

    void deleteUserWorkById(int id);

    void deleteUserCityById(int id);

    void deleteUserBusinessInfoById(int id);

    int getAutoPassApply(String staffId);

    List<Map<String, Object>> getGroupNum();

    List<String> getGroupInfo();

    UserDetail getUserDetailInfoByUserId(String userId);

    int updateForJob(@Param("staffId") String staffId,
                     @Param("location")String location,
                     @Param("job")Integer job,
                     @Param("longitude")Double longitude,
                     @Param("latitude")Double latitude,
                     @Param("updateTime")Date updateTime);

    String getStaffIdByName(@Param("name")String name);

    String getUserByStaffId(@Param("id")String id);
}
