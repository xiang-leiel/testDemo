package com.quantchi.tianji.service.search.service.project.report;

import com.quantchi.tianji.service.search.dao.*;
import com.quantchi.tianji.service.search.model.*;
import com.quantchi.tianji.service.search.model.vo.project.ProjectReportVO;
import com.quantchi.tianji.service.search.service.project.impl.ProjectManageServiceImpl;
import com.quantchi.tianji.service.search.utils.DateUtils;
import com.quantchi.tianji.service.search.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Description 
 * @author leiel
 * @Date 2020/3/16 1:40 PM
 */
@Slf4j
@Service
public class ReportService {

    @Resource
    private ProjectManageServiceImpl projectManageServiceImpl;

    @Resource
    private DmWfNodezbMapper dmWfNodezbMapper;

    @Resource
    private DmCsLcztMapper dmCsLcztMapper;

    @Resource
    private WfLsZbMapper wfLsZbMapper;

    @Resource
    private WfLsChbMapper wfLsChbMapper;

    public Integer reportOne(ProjectReportVO projectReportVO, Integer userId, Boolean leaderFlag) {
        projectReportVO.setAuditStatus(1);
        projectReportVO.setAuditTime(DateUtils.changeFormatDateToSec(new Date()));
        projectReportVO.setAuditRemark("审核通过");

        //新增流程表数据
        Integer status = insertWfData(projectReportVO, leaderFlag);

        //更新项目信息
        if(status != null) {
            projectManageServiceImpl.updateProjectData(projectReportVO, status);
        }else {
            log.error("项目流程节点未设置,userId={}", userId);
            return null;
        }
        return status;
    }

    private Integer insertWfData(ProjectReportVO projectReportVO, Boolean leaderFlag) {

        DmCsLczt dmCsLczt = new DmCsLczt();
        //next为0说明是商务局
        DmWfNodezb dmWfNodezb = new DmWfNodezb();
        Integer node = null;
        if(leaderFlag) {
            //获取部门对应的下一个状态
            Integer next = dmWfNodezbMapper.selectNextByUserDm(projectReportVO.getUserId());
            //根据下一个流程设置代码获取状态
            dmWfNodezb = dmWfNodezbMapper.selectNextZtByNode(next);
            if(dmWfNodezb != null) {
                //根据流程状态代码 获取下级设置项目状态
                node = dmWfNodezb.getWfnodezbDm();
                dmCsLczt = dmCsLcztMapper.selectByPrimaryKey(dmWfNodezb.getLcztDm());
            }
        }else {
            //组员上报根据部门获取当前状态
            node = dmWfNodezbMapper.selectNodeByUserDm(projectReportVO.getUserId());
            Integer status = dmWfNodezbMapper.selectStatusByUserDm(projectReportVO.getUserId());
            dmCsLczt.setXmztDm(status);
        }

        //新增工作流主表 和流程子表
        WfLsZb wfLsZb = new WfLsZb();

        //根据项目基本信息id查询工作流子表获取最新流程主表id 为前序流程id
        wfLsZb.setPreWflsId(null);

        wfLsZb.setUserDm(projectReportVO.getUserId());
        //0为待办
        wfLsZb.setLczsbj(0);
        wfLsZb.setCzrq(new Date());
        wfLsZb.setWfzbId(UUIDUtils.getZhaoshangUUId());
        wfLsZb.setWfnodeszbDm(node);
        wfLsZbMapper.insertSelective(wfLsZb);

        //新增子表均（项目基本信息id不变，其他均可变）
        WfLsChb wfLsChb1 = new WfLsChb();
        wfLsChb1.setSpzt(projectReportVO.getAuditStatus());
        wfLsChb1.setWfzbId(wfLsZb.getWfzbId());
        wfLsChb1.setXmjbxxId(projectReportVO.getXmId());
        wfLsChb1.setUserDm(projectReportVO.getUserId());
        wfLsChb1.setYhycDm(projectReportVO.getYhycDm());
        wfLsChb1.setSpyj(projectReportVO.getAuditRemark());
        wfLsChb1.setCzrq(new Date());
        wfLsChbMapper.insertSelective(wfLsChb1);

        //暂缓
        if(projectReportVO.getAuditStatus() == 2) {
            return null;
        }

        return dmCsLczt.getXmztDm();
    }
}
