package com.quantchi.tianji.service.search.service.impl.project;

import com.quantchi.tianji.service.search.entity.project.Label;
import com.quantchi.tianji.service.search.dao.mapper.project.LabelMapper;
import com.quantchi.tianji.service.search.service.project.ILabelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 项目标签或投资领域或人才标签表 服务实现类
 * </p>
 *
 * @author leiel
 * @since 2020-06-30
 */
@Service
public class LabelServiceImpl extends ServiceImpl<LabelMapper, Label> implements ILabelService {

}
