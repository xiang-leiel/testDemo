package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.ProjectReportDTO;import com.quantchi.tianji.service.search.model.XmjbxxZb;import org.apache.ibatis.annotations.Param;import java.util.Date;import java.util.List;

public interface XmjbxxZbMapper {
    int deleteByPrimaryKey(String xmjbxxId);

    int insert(XmjbxxZb record);

    int insertSelective(XmjbxxZb record);

    XmjbxxZb selectByPrimaryKey(String xmjbxxId);

    int updateByPrimaryKeySelective(XmjbxxZb record);

    int updateByPrimaryKey(XmjbxxZb record);

    /**
     * @param projectReportDTO
     * @return
     */
    List<XmjbxxZb> queryReportDataAll(ProjectReportDTO projectReportDTO);

    List<Integer> statisticalProvince(@Param("deptDms") List<Integer> deptDms);

    List<XmjbxxZb> selectListLikeName(ProjectReportDTO projectReportDTO);

    Integer countByXmBq(@Param("deptDm") Integer deptDm,
                        @Param("bqdm") Integer bqdm,
                        @Param("status") Integer status,
                        @Param("startTime") Date startTime,
                        @Param("endTime") Date endTime);

    List<XmjbxxZb> selectReportByBqdm(@Param("deptDm") Integer deptDm,
                                      @Param("bqdm") Integer bqdm,
                                      @Param("status") Integer status,
                                      @Param("startTime") Date startTime,
                                      @Param("endTime") Date endTime);

    List<XmjbxxZb> selectReportByScal(@Param("deptDm") Integer deptDm,
                                      @Param("scale") Integer scale,
                                      @Param("status") Integer status,
                                      @Param("startTime") Date startTime,
                                      @Param("endTime") Date endTime);

    List<XmjbxxZb> selectSignByBqdm(@Param("deptDm") Integer deptDm,
                                    @Param("bqdm") Integer bqdm,
                                    @Param("status") Integer status,
                                    @Param("startTime") Date startTime,
                                    @Param("endTime") Date endTime);

    List<XmjbxxZb> selectSignByScal(@Param("deptDm") Integer deptDm,
                                    @Param("scale") Integer scale,
                                    @Param("status") Integer status,
                                    @Param("startTime") Date startTime,
                                    @Param("endTime") Date endTime);

    Integer countByScal(@Param("deptDm") Integer deptDm,
                        @Param("scale") Integer scale,
                        @Param("status") Integer status,
                        @Param("startTime") Date startTime,
                        @Param("endTime") Date endTime);

    Integer countReportByXmBq(@Param("deptDm") Integer deptDm,
                              @Param("bqdm") Integer bqdm,
                              @Param("startTime") Date startTime,
                              @Param("endTime") Date endTime);

    Integer countReportByScal(@Param("deptDm") Integer deptDm,
                              @Param("scale") Integer scale,
                              @Param("startTime") Date startTime,
                              @Param("endTime") Date endTime);

    Integer countReportByDeptDm(@Param("deptDm") Integer deptDm,
                                @Param("industryType") Integer industryType,
                                @Param("startTime") Date startTime,
                                @Param("endTime") Date endTime);

    List<XmjbxxZb> selectReportDataByDept(@Param("deptDm") Integer deptDm,
                                          @Param("startTime") Date startTime,
                                          @Param("endTime") Date endTime);

    List<XmjbxxZb> selectSignDataByDept(@Param("deptDm") Integer deptDm,
                                        @Param("startTime") Date startTime,
                                        @Param("endTime") Date endTime);

    Integer countSignByDeptDm(@Param("deptDm") Integer deptDm,
                              @Param("industryType") Integer industryType,
                              @Param("startTime") Date startTime,
                              @Param("endTime") Date endTime);

    Integer countSignByXmBq(@Param("deptDm") Integer deptDm,
                            @Param("bqdm") Integer bqdm,
                            @Param("startTime") Date startTime,
                            @Param("endTime") Date endTime);

    Integer countInvestData(@Param("deptDm") Integer deptDm,
                            @Param("investScal") Integer investScal,
                            @Param("startTime") Date startTime,
                            @Param("endTime") Date endTime);

    Integer selectBySql(@Param("sql") String sql);

    /**
     * 获取总数据
     *
     * @param projectReportDTO
     * @return
     */
    List<XmjbxxZb> getProjectDataByParam(ProjectReportDTO projectReportDTO);

    /**
     * 获取数量
     *
     * @param projectReportDTO
     * @return
     */
    Integer countProjectDataByParam(ProjectReportDTO projectReportDTO);

    Integer countMemberDataByUserDm(@Param("industryType") Integer industryType,
                                    @Param("projectIds") List<String> projectIds);

    Integer countMemberDataByXmBq(@Param("bqdm") Integer bqdm,
                                  @Param("projectIds") List<String> projectIds);

    List<String> getAllDataByDeptDm(Integer deptId);

    void deleteByProjectId(String projectId);

    List<String> queryReportDataAllDownExcel(ProjectReportDTO projectReportDTO);
}