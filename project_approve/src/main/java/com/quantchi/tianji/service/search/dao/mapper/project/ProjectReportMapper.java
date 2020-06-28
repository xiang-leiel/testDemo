package com.quantchi.tianji.service.search.dao.mapper.project;

import com.quantchi.tianji.service.search.entity.project.ProjectReport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quantchi.tianji.service.search.model.ProjectReportDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author leiel
 * @since 2020-02-01
 */
public interface ProjectReportMapper extends BaseMapper<ProjectReport> {

    /**
     * 根据条件,查询上报数据
     * @return
     */
    List<ProjectReport> queryReportDataByPage(ProjectReportDTO projectReportDTO);

    List<ProjectReport> selectListByBranch(@Param("branch") String branch, @Param("status") List<Integer> status, @Param("flowWork") Integer flowWork);

    List<ProjectReport> selectListLikeName(ProjectReportDTO projectReportDTO);
}
