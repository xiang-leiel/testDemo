package com.quantchi.tianji.service.search.service.project;

import com.quantchi.tianji.service.search.dao.flow.WfOrderProjectMapper;
import com.quantchi.tianji.service.search.dao.CodeDeptmentMapper;
import com.quantchi.tianji.service.search.dao.user.UserInfoMapper;
import com.quantchi.tianji.service.search.entity.flow.WfOrderProject;
import com.quantchi.tianji.service.search.entity.user.UserInfo;
import com.quantchi.tianji.service.search.enums.ProjectStatusEnum;
import com.quantchi.tianji.service.search.model.vo.ProjectReportVO;
import com.quantchi.tianji.service.search.service.impl.project.ProjectManageServiceImpl;
import com.quantchi.tianji.service.search.utils.DateUtils;
import com.quantchi.tianji.service.search.utils.SnakerEngineFacets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.snaker.engine.entity.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    private SnakerEngineFacets snakerEngineFacets;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private CodeDeptmentMapper codeDeptmentMapper;

    @Resource
    private WfOrderProjectMapper wfOrderProjectMapper;

    @Value("${deqing.processID}")
    private String processID;

    @Value("${deqing.reason}")
    private String reason;

    @Value("${deqing.endAuditRole}")
    private String endAuditRole;

    @Transactional
    public Integer reportOne(ProjectReportVO projectReportVO, Integer userId) {

        projectReportVO.setAuditStatus(1);
        projectReportVO.setAuditTime(DateUtils.changeFormatDateToSec(new Date()));
        projectReportVO.setAuditRemark("审核通过");

        //获取该用户对应的初审角色
        String firstRole = getFirstRole(userId);

        Integer status = null;

        if(endAuditRole.equals(firstRole)) {
            status = ProjectStatusEnum.WAIT_JUDGE.getCode();
        }else {
            status = ProjectStatusEnum.WAIT_AGAIN_AUDIT.getCode();
        }

        //调用流程引擎，创建流程实例
        String orderId = startFlow(userId, firstRole, endAuditRole);

        //关联项目id
        WfOrderProject wfOrderProject = new WfOrderProject();
        wfOrderProject.setOrderId(orderId);
        wfOrderProject.setProjectId(projectReportVO.getXmId());
        wfOrderProject.setCreateTime(new Date());
        wfOrderProjectMapper.insert(wfOrderProject);

        //更新项目信息
        projectManageServiceImpl.updateProjectData(projectReportVO, status);
        log.error("项目流程状态,userId={}，status={}", userId, status);

        return status;
    }

    /**
     * 获取初审角色
     * @param userId
     * @return
     */
    public String getFirstRole(Integer userId) {

        UserInfo userInfo = userInfoMapper.selectById(userId);

        //获取上级部门
        String role = codeDeptmentMapper.getParentDeptRole(userInfo.getDeptId());
        log.info("获取上级部门角色={}", role);

        return role;

    }

    /**
     * 开启流程
     * @param userId 当前登录用户
     * @param firstAuditRole 初审角色
     * @param endAuditRole 终审角色
     * @return
     */
    public String startFlow(Integer userId, String firstAuditRole, String endAuditRole) {
        String processId = processID;

        //当前用户的id  也可设置角色，如果该处设置角色，则所有具有同一角色的用户都可以看到同一条数据
        String operatorRole = userId.toString();

        //封装参数测试
        Map<String, Object> params = new HashMap<>();
        params.put("report.operator", operatorRole);
        params.put("reason", reason);
        if(StringUtils.isNotEmpty(firstAuditRole)) {
            params.put("firstAudit.operator", firstAuditRole);
        }
        params.put("endAudit.operator", endAuditRole);

        Order order = snakerEngineFacets.startAndExecute(processId, operatorRole, params);

        return order.getId();
    }

}
