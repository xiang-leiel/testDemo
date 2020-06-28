package com.quantchi.tianji.service.search.service.project;

import com.quantchi.tianji.service.search.dao.*;
import com.quantchi.tianji.service.search.model.*;
import com.quantchi.tianji.service.search.model.param.ProjectSearchParams;
import com.quantchi.tianji.service.search.model.vo.project.ProjectReportVO;
import com.quantchi.tianji.service.search.model.vo.project.ProjectVO;
import com.quantchi.tianji.service.search.service.department.DepartmentService;
import com.quantchi.tianji.service.search.service.sign.impl.UserInfoService;
import com.quantchi.tianji.service.search.utils.CurrencyUtils;
import com.quantchi.tianji.service.search.utils.DateUtils;
import com.quantchi.tianji.service.search.utils.ListPageUtil;
import com.quantchi.tianji.service.search.utils.ResultUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description 项目上报数据服务类
 * @author leiel
 * @Date 2020/2/3 11:08 AM
 */
@Service
public class ProjectReportManageService {

    @Resource
    private XmjbxxZbMapper xmjbxxZbMapper;

    @Resource
    private DepartmentService departmentService;

    @Resource
    private XmGlTzfMapper xmGlTzfMapper;

    @Resource
    private XmGlBqlyMapper xmGlBqlyMapper;

    @Resource
    private DmCsUserMapper dmCsUserMapper;

    @Resource
    private DmCsTzgmMapper dmCsTzgmMapper;

    @Resource
    private DmCsBqlyMapper dmCsBqlyMapper;

    @Resource
    private WfLsChbMapper wfLsChbMapper;

    @Resource
    private DmCsXzqhMapper dmCsXzqhMapper;

    @Resource
    private XmTzfMapper xmTzfMapper;

    @Resource
    private WfLsZbMapper wfLsZbMapper;

    @Resource
    private DmCsHbdwMapper dmCsHbdwMapper;

    @Resource
    private DmWfNodezbMapper dmWfNodezbMapper;

    /**
     * 标签服务
     * @param resultMap
     * @param workFlowId 流程状态代码
     * @return
     */
    public void labelreport(Map<String, Map<String, Integer>> resultMap, Integer workFlowId, Integer userId) {

        //统计地域有哪些
        Map<String, Integer> regionMap = new LinkedHashMap<>();

        //统计投资规模
        Map<String, Integer> investMap = new LinkedHashMap<>();

        //计算项目标签
        Map<String, Integer> labelMap = new LinkedHashMap<>();

        //计算领域标签
        Map<String, Integer> fieldMap = new LinkedHashMap<>();

        DmCsUser dmCsUser = dmCsUserMapper.selectByPrimaryKey(userId);

        List<Integer> deptDms = departmentService.getXjDeptDms(dmCsUser.getDeptDm());
        List<Integer> regions = xmjbxxZbMapper.statisticalProvince(deptDms);

        //地域
        for(Integer str : regions) {
            //根据省级行政区代码
            DmCsXzqh dmCsXzqh = dmCsXzqhMapper.selectByPrimaryKey(str);
            if(dmCsXzqh != null) {
                regionMap.put(dmCsXzqh.getXzqhmc(), str);
            }
        }

        //投资规模、
        List<DmCsTzgm> dmCsTzgms = dmCsTzgmMapper.selectTzgmAll();
        for(int i = 0; i < dmCsTzgms.size(); i++) {
            investMap.put(dmCsTzgms.get(i).getTzgmMc(),dmCsTzgms.get(i).getTzgmDm());
        }
        //项目标签 或行业领域
        List<DmCsBqly> dmCsBqlies = dmCsBqlyMapper.selectBqlyAll();
        for(DmCsBqly dmCsBqly : dmCsBqlies) {
            if(dmCsBqly.getBqlybj() == 1 && dmCsBqly.getSjbqlyDm() == 1001000){
                labelMap.put(dmCsBqly.getBqlyMc(), dmCsBqly.getBqlyDm());
            }else if(dmCsBqly.getBqlybj() == 2 && dmCsBqly.getCjbj() == 2){
                fieldMap.put(dmCsBqly.getBqlyMc(), dmCsBqly.getBqlyDm());
            }
        }

        resultMap.put("regions", regionMap);
        resultMap.put("invest", investMap);
        resultMap.put("label", labelMap);
        resultMap.put("field", fieldMap);

    }

    /**
     * 项目查询服务
     * @param projectSearchParams
     * @return
     */
    public ProjectVO searchProject(ProjectSearchParams projectSearchParams) {

        List<String> projectIds = new ArrayList<>();

        ProjectVO projectVO = new ProjectVO();

        if(projectSearchParams.getStatusList().size() > 0 &&
                projectSearchParams.getStatusList().size() != 1 && projectSearchParams.getStatusList().get(0) != 7){
            //根据人员 岗位关系获取哪些待办的数据   0是待办
            Integer node = dmWfNodezbMapper.selectNodDmByUserId(projectSearchParams.getUserId());


            if(projectSearchParams.getDealFlag() == 0) {
                //根据node查询流水主表查看待办
                projectIds = wfLsZbMapper.selectListByNode(node , 0);
            }else {
                projectIds = wfLsZbMapper.selectListByNode(node , 1);

            }

            if(CollectionUtils.isEmpty(projectIds)) {
                projectVO.setTotal(0);
                return projectVO;
            }
        }

        ProjectReportDTO projectReportDTO = new ProjectReportDTO();

        BeanUtils.copyProperties(projectSearchParams, projectReportDTO);

        //先根据时间、地区及行业领域获取相关数据
        if(projectSearchParams.getReportStartTime() != null && projectSearchParams.getReportStartTime() != null) {
            projectReportDTO.setReportStartTime(DateUtils.getDateYYYYMMddHHMMSS(DateUtils.dealTimeData(projectSearchParams.getReportStartTime())));
            projectReportDTO.setReportEndTime(DateUtils.getDateYYYYMMddHHMMSS(DateUtils.dealTimeData(projectSearchParams.getReportEndTime())));
        }
        projectReportDTO.setRegions(projectReportDTO.getRegions());
        //项目状态 岗位获取流转状态代码对应项目状态  大于当前岗位状态的数据
        projectReportDTO.setStatusList(projectSearchParams.getStatusList());

        if(StringUtils.isEmpty(projectReportDTO.getProjectName())) {
            projectReportDTO.setProjectName(null);
        }

        List<XmjbxxZb> xmjbxxZbs = xmjbxxZbMapper.queryReportDataAll(projectReportDTO);

        Iterator<XmjbxxZb> it = xmjbxxZbs.iterator();

        while(it.hasNext()) {

            XmjbxxZb xmjbxxZb = it.next();
            if(CollectionUtils.isNotEmpty(projectIds) && !projectIds.contains(xmjbxxZb.getXmjbxxId())) {
                it.remove();
                continue;
            }

            //过滤非该岗位能看到的数据
            if(projectSearchParams.getDepartmentId() != null){
                Boolean deptFlag = departmentService.checkDepartment(projectSearchParams.getDepartmentId(), xmjbxxZb.getZszDeptDm());
                if(!deptFlag) {
                    it.remove();
                    continue;
                }
            }

            //查询终止数据 是否是当前节点或者大于当前节点可查数据
            if(xmjbxxZb.getXmztDm() == 1001 && projectSearchParams.getStatusList().contains(1001)) {

                Integer deptDm = wfLsChbMapper.selectDeptDmByXmId(xmjbxxZb.getXmjbxxId());

                Boolean endFlag = departmentService.checkDepartment(deptDm, projectSearchParams.getDepartmentId());

                if(!deptDm.equals(projectSearchParams.getDepartmentId()) && !endFlag) {
                    it.remove();
                    continue;
                }

            }

        }

        turnToShowData(xmjbxxZbs, projectVO, projectSearchParams.getUserId(), projectSearchParams.getPage(), projectSearchParams.getPageSize());

        return projectVO;

    }

    public void turnToShowData(List<XmjbxxZb> xmjbxxZbs, ProjectVO projectVO, Integer userId, int page, int pageSize){

        List<ProjectReportVO> projectReportVOList = new ArrayList<>();
        //数据转换
        for(XmjbxxZb xmjbxxZb : xmjbxxZbs) {
            ProjectReportVO projectReportVO = new ProjectReportVO();
            copyProperties(userId, xmjbxxZb, projectReportVO);
            //上报时间
            projectReportVO.setAuditTime(DateUtils.changeFormatDateToSec(xmjbxxZb.getSbrq()));
            if(xmjbxxZb.getZszDeptDm() != null) {
                projectReportVO.setDepartment(departmentService.selectDeptByDeptDm(xmjbxxZb.getZszDeptDm()));
            }
            projectReportVOList.add(projectReportVO);
        }

        projectVO.setTotal(xmjbxxZbs.size());

        //分页返回
        if(CollectionUtils.isNotEmpty(projectReportVOList)) {
            projectReportVOList = ListPageUtil.getListPage(page, pageSize, projectReportVOList);

        }
        projectVO.setProjectReportVOs(projectReportVOList);
    }

    public void copyProperties(Integer userId, XmjbxxZb xmjbxxZb, ProjectReportVO projectReportVO) {

        projectReportVO.setXmId(xmjbxxZb.getXmjbxxId());
        projectReportVO.setStatus(xmjbxxZb.getXmztDm());

        //如果状态和当前登录部门对应的状态一样，查询最新的一条流程记录是否暂缓
        //如果为暂缓，则将返回的数据状态设置为1002
        if(userId != null) {
            Integer userStatus = dmWfNodezbMapper.selectStatusDmByUserId(userId);
            if(xmjbxxZb.getXmztDm().equals(userStatus)) {
                //查询最新的一条记录是否为暂缓
                Integer flowStatus = wfLsChbMapper.selectStatusDmByXmId(xmjbxxZb.getXmjbxxId());
                if(flowStatus != null && flowStatus == 2) {
                    projectReportVO.setStatus(1002);
                }
            }
        }
        projectReportVO.setProjectName(xmjbxxZb.getXmmc());
        projectReportVO.setIndustryType(xmjbxxZb.getCylxBj());
        projectReportVO.setProjectInfo(xmjbxxZb.getXmNr());
        projectReportVO.setNeedLand(xmjbxxZb.getXmXyd());
        projectReportVO.setNeedLandUnit(xmjbxxZb.getXydUnit());

        String needLandUnit = "亩";
        if(xmjbxxZb.getXydUnit() == 2) {
            needLandUnit = "平方米";
        }
        if(xmjbxxZb.getXmXyd() != null) {
            projectReportVO.setNeedLandNew(xmjbxxZb.getXmXyd().setScale(0, BigDecimal.ROUND_UP) + needLandUnit);
        }
        projectReportVO.setNeedLandType(xmjbxxZb.getXmYdlxbj());
        DmCsHbdw dmCsHbdw = dmCsHbdwMapper.selectByPrimaryKey(xmjbxxZb.getHbdwDm());

        if(dmCsHbdw != null && xmjbxxZb.getXmGdtzzc() != null) {
            if(xmjbxxZb.getXmGdtzzc().compareTo(BigDecimal.ZERO) > 0) {
                //处理为万元  或亿元
                projectReportVO.setAssetInvestNew(CurrencyUtils.getCurrency(xmjbxxZb.getXmGdtzzc().setScale(0, BigDecimal.ROUND_UP)) + dmCsHbdw.getHbdwMc());
            }
        }
        if(xmjbxxZb.getXmGdtzzc() != null) {
            projectReportVO.setAssetInvest(xmjbxxZb.getXmGdtzzc());
        }
        projectReportVO.setCurrencyUnit(xmjbxxZb.getHbdwDm());
        projectReportVO.setCurrencyUnitTotal(xmjbxxZb.getHbdwDm());

        //根据上报人id获取对应的中文名称
        if(xmjbxxZb.getSbrUserDm() != null) {
            String reportors = dealReportName(xmjbxxZb.getSbrUserDm());
            projectReportVO.setStaffName(reportors);
        }

        DmCsDept dmCsDept = departmentService.selectDeptInfo(xmjbxxZb.getZszDeptDm());

        if(dmCsDept!= null) {
            projectReportVO.setGroup(dmCsDept.getBmmc());
            projectReportVO.setRegionFlag(dmCsDept.getDeptDydm());
        }

        projectReportVO.setSuggestLand(xmjbxxZb.getLdptDeptDm());
        if(xmjbxxZb.getLdptDeptDm() != null) {
            projectReportVO.setSuggestLandNew(departmentService.selectDeptByDeptDm(xmjbxxZb.getLdptDeptDm()));
        }

        List<String> address = new ArrayList<>();
        if(xmjbxxZb.getTzfXzqhDm() != null) {
            DmCsXzqh dmCsXzqh = dmCsXzqhMapper.selectByPrimaryKey(xmjbxxZb.getTzfXzqhDm());
            address.add("中国");
            address.add(dmCsXzqh.getXzqhmc());
        }
        //总投资
        projectReportVO.setInvestTotal(xmjbxxZb.getXmZtz());
        if(xmjbxxZb.getXmZtz() != null) {
            if(xmjbxxZb.getXmZtz().compareTo(BigDecimal.ZERO) > 0) {
                projectReportVO.setInvestTotalNew(CurrencyUtils.getCurrency(xmjbxxZb.getXmZtz().setScale(0, BigDecimal.ROUND_UP)) + dmCsHbdw.getHbdwMc());
            }
        }
        projectReportVO.setRemark(xmjbxxZb.getBz());


        List<XmGlTzf> xmGlTzfs = xmGlTzfMapper.selectByProjectId(xmjbxxZb.getXmjbxxId());

        if(CollectionUtils.isNotEmpty(xmGlTzfs)) {
            XmTzf xmTzf = xmTzfMapper.selectByPrimaryKey(xmGlTzfs.get(0).getTzfId());
            projectReportVO.setInvestName(xmTzf.getTzfmc());
        }

        //新增字段
        if(xmjbxxZb.getTzyxrq() != null) {
            projectReportVO.setMoveTime(getInvestmentDate(xmjbxxZb.getTzyxrq(), xmjbxxZb.getCzrq()));
        }
        projectReportVO.setOfferArea(xmjbxxZb.getXmBgmj());
        projectReportVO.setOtherRequire(xmjbxxZb.getXmQtyq());


        //项目是否已被隐藏
        projectReportVO.setHide(xmjbxxZb.getXmYcbj());

        //首报人
        if(xmjbxxZb.getMasterUserDm() != null) {
            DmCsUser dmCsUser = dmCsUserMapper.selectByPrimaryKey(xmjbxxZb.getMasterUserDm());
            projectReportVO.setMasterName(dmCsUser.getXm());
            projectReportVO.setMasterUserId(xmjbxxZb.getMasterUserDm());
        }

    }

    public String dealReportName(String reportIds) {

        if(StringUtils.isBlank(reportIds)) {
            return null;
        }
        String[] reportor = reportIds.split("\\|");

        StringBuffer sb = new StringBuffer();
        for(String str : reportor) {
            DmCsUser dmCsUser = dmCsUserMapper.selectByPrimaryKey(Integer.valueOf(str));
            if(dmCsUser == null) {
                continue;
            }
            sb.append(dmCsUser.getXm());
            sb.append(",");
        }
        if(sb == null || sb.length() == 0) {
            return null;
        }

        String result = sb.toString();

        return result.substring(0, result.length()-1);

    }

    private String getInvestmentDate(Date tzyxrq, Date czrq){
        String investmentDate = "";
        Calendar now = Calendar.getInstance();
        now.setTime(czrq);
        Calendar future = Calendar.getInstance();
        future.setTime(tzyxrq);

        int futureYear = future.get(Calendar.YEAR);
        int futureMonth = future.get(Calendar.MONTH);

        int nowYear = now.get(Calendar.YEAR);
        int nowMonth = now.get(Calendar.MONTH);

        int month = (futureYear-nowYear)*12+futureMonth-nowMonth;

        if(futureYear-nowYear==5){
            return "五年";
        }
        else if(futureYear-nowYear==3){
            return "三年";
        }
        else if(futureYear-nowYear==1){
            return "一年";
        }
        else if(month==6){
            return "半年";
        }
        else {
            return "三月";
        }

    }

    public Date getTzyxrq(String investmentDate){
        Date tzyxrq;
        SimpleDateFormat sdf = new SimpleDateFormat("yy-mm-dd");
        Calendar c1 = Calendar.getInstance();

        if(investmentDate.equals("三月")){
            c1.add(Calendar.MONTH,3);
        }
        else if(investmentDate.equals("半年")){
            c1.add(Calendar.MONTH,6);
        }
        else if(investmentDate.equals("一年")){
            c1.add(Calendar.YEAR,1);
        }
        else if(investmentDate.equals("三年")){
            c1.add(Calendar.YEAR,3);
        }
        else if(investmentDate.equals("五年")){
            c1.add(Calendar.YEAR,5);
        }
        // 未知
        else{
            return null;
        }
        tzyxrq = c1.getTime();
        return tzyxrq;
    }

    @Resource
    private VisitRecordMapper visitRecordMapper;

    public ProjectVO reportSearch(ProjectSearchParams projectSearchParams){

        ProjectVO projectVO = new ProjectVO();

        //根据userId获取部门
        Integer deptId = dmCsUserMapper.getDeptDmByUserId(projectSearchParams.getUserId());

        //校验是否为组长
        Boolean isLeader = departmentService.checkLeader(projectSearchParams.getUserId());

        ProjectReportDTO projectReportDTO = new ProjectReportDTO();

        BeanUtils.copyProperties(projectSearchParams, projectReportDTO);

        //先根据时间、地区及行业领域获取相关数据
        if(projectSearchParams.getReportStartTime() != null && projectSearchParams.getReportStartTime() != null) {
            projectReportDTO.setReportStartTime(DateUtils.getDateYYYYMMddHHMMSS(DateUtils.dealTimeData(projectSearchParams.getReportStartTime())));
            projectReportDTO.setReportEndTime(DateUtils.getDateYYYYMMddHHMMSS(DateUtils.dealTimeData(projectSearchParams.getReportEndTime())));
        }
        projectReportDTO.setRegions(projectReportDTO.getRegions());
        //项目状态 岗位获取流转状态代码对应项目状态  大于当前岗位状态的数据
        projectReportDTO.setStatusList(projectSearchParams.getStatusList());

        //添加部门
        List<Integer> deptList = new ArrayList<>();
        deptList.add(deptId);
        projectReportDTO.setDepartmentList(deptList);

        List<XmjbxxZb> xmjbxxZbs = xmjbxxZbMapper.queryReportDataAll(projectReportDTO);

        List<String> visitIds = new ArrayList<>();
        if(!isLeader){

            List<String> projectMaster = visitRecordMapper.selectXmIdByMasterUser(projectSearchParams.getUserId());

            List<String> projectOther = visitRecordMapper.selectXmIdByOtherUser(projectSearchParams.getUserId());

            //组员看自己或为同行人的数据
            visitIds.addAll(projectOther);
            visitIds.addAll(projectMaster);

        }

        Iterator<XmjbxxZb> it = xmjbxxZbs.iterator();

        while(it.hasNext()) {

            XmjbxxZb xmjbxxZb = it.next();

            //查询终止数据 是否是当前节点或者大于当前节点可查数据
            if(xmjbxxZb.getXmztDm() == 1001 && projectSearchParams.getStatusList().contains(1001)) {

                Integer deptDm = wfLsChbMapper.selectDeptDmByXmId(xmjbxxZb.getXmjbxxId());

                Boolean endFlag = departmentService.checkDepartment(deptDm, projectSearchParams.getDepartmentId());

                if(!projectSearchParams.getDepartmentId().equals(deptDm) && !endFlag) {
                    it.remove();
                    continue;
                }

            }

            //如果不是组长校验该项目是否与该用户相关
            if(!isLeader){
                if(CollectionUtils.isNotEmpty(visitIds) && !visitIds.contains(xmjbxxZb.getXmjbxxId())) {
                    it.remove();
                    continue;
                }

            }

        }

        turnToShowData(xmjbxxZbs, projectVO, projectSearchParams.getUserId(), projectSearchParams.getPage(), projectSearchParams.getPageSize());

        return projectVO;
    }

}
