package com.quantchi.tianji.service.search.dao.project;

import com.quantchi.tianji.service.search.entity.project.ProjectLabel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 项目标签关联表 Mapper 接口
 * </p>
 *
 * @author leiel
 * @since 2020-06-30
 */
public interface ProjectLabelMapper extends BaseMapper<ProjectLabel> {

    void updateInvalidByXmId(String xmId, int i);
}
