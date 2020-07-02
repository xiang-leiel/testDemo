package com.quantchi.tianji.service.search.dao.project;

import com.quantchi.tianji.service.search.entity.project.ProjectInvest;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 项目投资方信息表 Mapper 接口
 * </p>
 *
 * @author leiel
 * @since 2020-06-30
 */
public interface ProjectInvestMapper extends BaseMapper<ProjectInvest> {

    List<ProjectInvest> selectByProjectId(String projectId);

    void deleteByProjectId(String projectId);
}
