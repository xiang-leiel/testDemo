package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.SignRecord;
import com.quantchi.tianji.service.search.model.database.VisitRecordDTO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SignRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SignRecord record);

    int insertSelective(SignRecord record);

    SignRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SignRecord record);

    int updateByPrimaryKey(SignRecord record);

    /**
     * 根据条件查询相关数据信息
     *
     * @return
     */
    List<SignRecord> selectByStaff(@Param("staffId") String staffId, @Param("staffGroup") String staffGroup,
                                   @Param("startTime") Date startTime,
                                   @Param("endTime") Date endTime);

    /**
     * 根据条件查询用户签到记录
     *
     * @param staffId
     * @param startTime
     * @param endTime
     * @return
     */
    List<String> selectByStaffForSign(@Param("staffId") String staffId,
                                      @Param("startTime") Date startTime,
                                      @Param("endTime") Date endTime);

    /**
     * 根据条件查询用户签到记录
     *
     * @param staffId
     * @param startTime
     * @param endTime
     * @return
     */
    Double getDistanceByStaff(@Param("staffId") String staffId,
                              @Param("startTime") Date startTime,
                              @Param("endTime") Date endTime);

    /**
     * 根据条件查询用户签到记录
     *
     * @param staffId
     * @param startTime
     * @param endTime
     * @return
     */
    List<VisitRecordDTO> getVisitByStaff(@Param("staffId") String staffId,
                                         @Param("startTime") Date startTime,
                                         @Param("endTime") Date endTime);

    /**
     * 获取单个人的拜访次数
     *
     * @param staffId
     * @param startTime
     * @param endTime
     * @return
     */
    SignRecord fetchOne(@Param("staffId") String staffId,
                        @Param("startTime") Date startTime,
                        @Param("endTime") Date endTime);

    /**
     * 获取组
     *
     * @param staffGroup
     * @param startTime
     * @param endTime
     * @return
     */
    List<VisitRecordDTO> getVisitByGroup(@Param("staffGroup") String staffGroup,
                                         @Param("staffId") String staffId,
                                         @Param("startTime") Date startTime,
                                         @Param("endTime") Date endTime,
                                         @Param("visitFlag") Integer visitFlag);

    int updateById(SignRecord record);

    SignRecord selectOne(@Param("id") String id,
                         @Param("staffId") String staffId,
                         @Param("visitLocation") String visitLocation,
                         @Param("startTime") Date startTime,
                         @Param("endTime") Date endTime);

    SignRecord selectByVisitId(@Param("visitId") String visitId,
                               @Param("staffId") String staffId,
                               @Param("startTime") Date startTime,
                               @Param("endTime") Date endTime);
}