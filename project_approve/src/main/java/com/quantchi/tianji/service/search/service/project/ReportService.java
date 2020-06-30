package com.quantchi.tianji.service.search.service.project;

import com.quantchi.tianji.service.search.model.vo.ProjectReportVO;
import com.quantchi.tianji.service.search.service.impl.project.ProjectManageServiceImpl;
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


    public Integer reportOne(ProjectReportVO projectReportVO, Integer userId, Boolean leaderFlag) {
        projectReportVO.setAuditStatus(1);
        projectReportVO.setAuditTime(DateUtils.changeFormatDateToSec(new Date()));
        projectReportVO.setAuditRemark("审核通过");

        //获取下一阶段流程状态
        Integer status = null;

        //更新项目信息
        if(status != null) {
            projectManageServiceImpl.updateProjectData(projectReportVO, status);
        }else {
            log.error("项目流程节点未设置,userId={}", userId);
            return null;
        }
        return status;
    }

}
