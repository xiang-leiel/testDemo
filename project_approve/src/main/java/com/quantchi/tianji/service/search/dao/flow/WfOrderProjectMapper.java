package com.quantchi.tianji.service.search.dao.flow;

import com.quantchi.tianji.service.search.entity.flow.WfOrderProject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author leiel
 * @since 2020-06-30
 */
public interface WfOrderProjectMapper extends BaseMapper<WfOrderProject> {

    String selectOrderId(String id);
}
