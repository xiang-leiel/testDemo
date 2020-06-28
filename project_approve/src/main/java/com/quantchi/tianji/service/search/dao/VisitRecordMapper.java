package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.VisitRecord;import com.quantchi.tianji.service.search.model.vo.ProjectInfo;import org.apache.ibatis.annotations.Param;import java.util.Date;import java.util.List;

public interface VisitRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(VisitRecord record);

    int insertSelective(VisitRecord record);

    VisitRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(VisitRecord record);

    int updateByPrimaryKey(VisitRecord record);

    int updateById(@Param("id") Long id, @Param("updateTime") Date updateTime);

    Integer countByStatffId(@Param("staffId") String staffId, @Param("visitStatus") Integer visitStatus);

    List<ProjectInfo> fetchData(@Param("status") Integer status,
                                @Param("startTime") Date startTime,
                                @Param("endTime") Date endTime,
                                @Param("page") Integer page,
                                @Param("pagesize") Integer pagesize);

    List<ProjectInfo> queryProjectData(@Param("statusList") List<Integer> statusList,
                                       @Param("page") Integer page,
                                       @Param("pagesize") Integer pagesize);

    Integer countProjectData(@Param("statusList") List<Integer> statusList);

    List<ProjectInfo> fetchByGroup(@Param("status") Integer status, @Param("group") String group,
                                   @Param("page") Integer page,
                                   @Param("pagesize") Integer pagesize);

    List<ProjectInfo> fetchByGroupNoPage(@Param("status") Integer status, @Param("group") String group);

    List<ProjectInfo> fetchByStaffId(@Param("status") Integer status, @Param("staffId") String staffId,
                                     @Param("page") Integer page,
                                     @Param("pagesize") Integer pagesize);

    Integer countDataByGroup(@Param("statusList") List<Integer> statusList, @Param("group") String group);

    List<ProjectInfo> fetchDataByGroup(@Param("statusList") List<Integer> statusList, @Param("group") String group);

    List<ProjectInfo> fetchDataByGroupAndTime(@Param("statusList") List<Integer> statusList,
                                              @Param("group") String group,
                                              @Param("startTime") Date startTime,
                                              @Param("endTime") Date endTime);

    //int updateByKeyId(@Param("id") Long id, @Param("updateTime") Date updateTime, @Param("status") Integer status);

    List<ProjectInfo> fetchDataByStaff(@Param("staffId") String staffId);

    ProjectInfo fetchDataById(@Param("id") Long id);

    Integer fetchCountByStaffId(@Param("staffId") String staffId, @Param("status") Integer status, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    Integer fetchCountByStaffIdNew(@Param("staffId") String staffId, @Param("status") Integer status, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    Integer fetchCountByGroup(@Param("status") Integer status, @Param("group") String group);

/*    VisitRecord selectByVisitName(@Param("visitName") String visitName,
                                  @Param("staffId") String staffId,
                                  @Param("together") Integer together);

    List<ProjectInfo> fetchListByName(@Param("visitName") String visitName,
                                      @Param("page") Integer page,
                                      @Param("pagesize") Integer pagesize);

    VisitRecord selectByMasterId(@Param("masterId") Long masterId,
                                 @Param("staffId") String staffId,
                                 @Param("together") Integer together);*/

    ProjectInfo fetchLastDataLeader(@Param("status") Integer status);

    List<ProjectInfo> fetchLastData(@Param("status") Integer status, @Param("group") String group);

    List<ProjectInfo> fetchVisitingData(@Param("statusList") List<Integer> statusList,
                                        @Param("group") String group);
    List<Integer> selectByMasterId(String masterId);

    List<String> selectXmIdBydeptDm(Integer deptId);

    String queryRelDataByXmId(@Param("masterId") String masterId, @Param("userId") Integer userId);

    List<String> selectXmIdByMasterUser(@Param("userId") Integer userId);

    List<String> selectXmIdByOtherUser(@Param("userId") Integer userId);

    List<String> getAllDataByDeptDm(Integer deptId);

    Integer selectByVisitId(String visitId);

    void deleteByMasterId(String projectId);

    void deleteByProjectId(String projectId);
}