package com.quantchi.tianji.service.search.dao.project;

import com.quantchi.tianji.service.search.entity.project.ProjectInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quantchi.tianji.service.search.service.project.ProjectReportDTO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 项目主表 Mapper 接口
 * </p>
 *
 * @author leiel
 * @since 2020-06-30
 */
public interface ProjectInfoMapper extends BaseMapper<ProjectInfo> {

    List<ProjectInfo> queryReportDataAll(ProjectReportDTO projectReportDTO);

    void deleteByProjectId(String projectId);

    Integer countInvestData(@Param("deptDm") Integer deptDm,
                            @Param("investScal") Integer investScal,
                            @Param("startTime") Date startTime,
                            @Param("endTime") Date endTime);

    int countSignByDeptDm(@Param("deptDm") Integer deptDm,
                          @Param("industryType") Integer industryType,
                          @Param("startTime") Date startTime,
                          @Param("endTime") Date endTime);

    int countSignByXmBq(@Param("deptDm") Integer deptDm,
                        @Param("bqdm") Integer bqdm,
                        @Param("startTime") Date startTime,
                        @Param("endTime") Date endTime);
}
