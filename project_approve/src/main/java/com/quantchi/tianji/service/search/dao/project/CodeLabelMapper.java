package com.quantchi.tianji.service.search.dao.project;

import com.quantchi.tianji.service.search.entity.project.CodeLabel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 项目标签或投资领域或人才标签表 Mapper 接口
 * </p>
 *
 * @author leiel
 * @since 2020-07-01
 */
public interface CodeLabelMapper extends BaseMapper<CodeLabel> {

    List<CodeLabel> selectBqlyAll();
}
