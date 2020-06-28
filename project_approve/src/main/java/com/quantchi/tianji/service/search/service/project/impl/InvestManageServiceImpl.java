package com.quantchi.tianji.service.search.service.project.impl;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.dao.XmGlTzfMapper;
import com.quantchi.tianji.service.search.dao.XmTzfMapper;
import com.quantchi.tianji.service.search.model.XmGlTzf;
import com.quantchi.tianji.service.search.model.XmTzf;
import com.quantchi.tianji.service.search.service.project.InvestManageService;
import com.quantchi.tianji.service.search.utils.ResultUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Description 
 * @author leiel
 * @Date 2020/3/16 10:51 AM
 */
@Service
public class InvestManageServiceImpl implements InvestManageService {

    @Resource
    private XmGlTzfMapper xmGlTzfMapper;

    @Resource
    private XmTzfMapper xmTzfMapper;

    @Transactional
    @Override
    public ResultInfo delete(String investId) {

        //逻辑删除投资方xm_tzf
        XmGlTzf xmGlTzf = new XmGlTzf();
        xmGlTzf.setTzfId(investId);
        xmGlTzf.setQybj(0);
        xmGlTzfMapper.updateQybjByTzfId(xmGlTzf);

        //逻辑删除xm_gl_tzf
        XmTzf xmTzf = new XmTzf();
        xmTzf.setTzfId(investId);
        xmTzf.setQybj(0);
        xmTzfMapper.updateQybjByTzfId(xmTzf);

        return ResultUtils.success("success");
    }
}
