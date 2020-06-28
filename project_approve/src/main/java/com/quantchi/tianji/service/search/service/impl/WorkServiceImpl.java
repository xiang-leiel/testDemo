package com.quantchi.tianji.service.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.config.AppConfig;
import com.quantchi.tianji.service.search.dao.*;
import com.quantchi.tianji.service.search.enums.ProjectStatusEnum;
import com.quantchi.tianji.service.search.enums.TypeEnums;
import com.quantchi.tianji.service.search.exception.TianjiErrorCode;
import com.quantchi.tianji.service.search.helper.ElasticsearchHelper;
import com.quantchi.tianji.service.search.helper.SearchUtil;
import com.quantchi.tianji.service.search.model.*;
import com.quantchi.tianji.service.search.service.EventService;
import com.quantchi.tianji.service.search.service.WorkTaskService;
import com.quantchi.tianji.service.search.service.sign.impl.SignDealService;
import com.quantchi.tianji.service.search.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @program: DeQing-InvestmentEnterprise
 * @author: mf
 * @create: 2019-07-09 10:07
 **/
@Service
@Slf4j
public class WorkServiceImpl implements WorkTaskService {

    @Value("${hs.url}")
    private String hsUrl;

    @Autowired
    private ElasticsearchHelper elasticsearchHelper;

    @Autowired
    private SearchUtil searchUtil;


    @Autowired
    private AppConfig appConfig;

    @Autowired
    private WorkTaskDao workTaskDao;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StatusLogDao statusLogDao;

    @Autowired
    private UserDao userDao;

    @Value("${pic.url}")
    private String picUrl;

    @Autowired
    private WorkCircleDao workCircleDao;
    @Autowired
    private EventService eventService;

    @Resource
    private VisitRecordMapper visitRecordMapper;

    @Resource
    private SignDealService signDealService;


    @Value("${event.index}")
    private String eventIndex;

    @Value("${event.type}")
    private String eventType;

    @Value("${index}")
    private String index;

    @Value("${type}")
    private String type;

    @Override
    public Map<String,Object> listWorkTasksByUserid(String staffId, Long intervalTime, Integer isAccept, Integer page, Integer pageSize) {
        page = page == null ? 1 : page;
        pageSize = pageSize == null ? 10 : pageSize;
        //UserDetail userDetail = userDao.getUserDetail(staffId);
        User userDetail = userDao.getUserInfo(staffId);
        Date startTime = null;
        if (userDetail!=null) {
            startTime = userDetail.getMessageTime();
        }
        PageHelper.startPage(page, pageSize);

        //指派给我的任务列表
        List<WorkTask> workTasks = workTaskDao.listWorkTasksByUserid(staffId, startTime, isAccept);
        if (null != workTasks && workTasks.size() > 0) {

            List<WorkTask> lzTasks = getLzCompanyDetails(workTasks);

            List<WorkTask> hsTasks = getHsCompanyDetailsFromWeb(workTasks);

            List<WorkTask> hsTasksByApp = getHsCompanyDetailsFromApp(workTasks);

            lzTasks.addAll(hsTasks);

            lzTasks.addAll(hsTasksByApp);

            workTasks = lzTasks.stream()
                    .sorted(Comparator.comparing(WorkTask::getCreateTime).reversed())
                    .collect(Collectors.toList());
        }
        PageInfo<WorkTask> pageInfo = new PageInfo<>(workTasks);
        List<WorkTask> list = pageInfo.getList();
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("total", pageInfo.getTotal());
        resultMap.put("pages", pageInfo.getPages());
        List<Map<String,Object>> resultList = new ArrayList<>();
        resultMap.put("list", resultList);
        for (WorkTask workTask:list) {
            Map<String,Object> taskMap = new HashMap<>();
            taskMap.put("task", workTask);
            resultList.add(taskMap);
        }
        return resultMap;
    }

    /**
     * 调用火石的接口返回数据详情
     * @param hsIdList
     * @return
     */
    private List<Map<String, Object>> getHsCompanyDetail(List<String> hsIdList) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("companyIds", hsIdList);
        HttpEntity<Map<String,Object>> formEntity = new HttpEntity<Map<String,Object>>(paramMap, headers);
        String result = "";
        Map<String, Object> respMap = new HashMap<>();
        try {
            result = restTemplate.postForObject(hsUrl, formEntity, String.class);
            log.info("调用火石WEB接口成功,返回result:{}",result);
        } catch (Throwable e) {
            log.error("调用火石WEB数据接口失败,参数信息:{},异常信息:{}", paramMap, e.getMessage());
        }
        respMap = JSON.parseObject(result, Map.class);
        List<Map<String, Object>> respList = (List<Map<String, Object>>) respMap.get("data");
        List<Map<String,Object>> resultList = new ArrayList<>();
        for (Map<String, Object> body: respList) {
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("area", body.get("address_city"));
            resultMap.put("id", body.get("company_id"));
            if (body.get("iconurl") == null || body.get("iconurl").toString().equals("")) {
                resultMap.put("logo", picUrl+"/pic/default/webwxgetmsgimg.jpg");
            } else {
                resultMap.put("logo", body.get("iconurl"));
            }
            resultMap.put("name", body.get("name"));
            resultMap.put("briefIntroduction", body.get("describer"));
            resultMap.put("baseInfo", body.get("describer"));
            resultMap.put("financeRound", body.get("financeRound"));
            resultMap.put("location", body.get("address"));
            Map<String,Object> informationMap = new HashMap<>();
            informationMap.put("成立日期", body.get("chengliriqi"));
            resultMap.put("information", informationMap);
            resultMap.put("recommendReason", body.get("investmentRecommendReason"));
            resultMap.put("parent", "");
            resultMap.put("industry", body.get("industry"));
            resultList.add(resultMap);
        }
        return resultList;
    }

    @Override
    @Transactional
    public ResultInfo assign(WorkTask workTask) {
        User userInfo = userDao.getUserInfo(workTask.getStaffId());
        if(userInfo.getRoleName().equals("招商员")) {
            if (!workTask.getStaffId().equals(workTask.getHandlerId())) {
                return ResultUtils.fail(TianjiErrorCode.NOT_ALLOWED_ASSIGN_ERROR);
            }
        }
        WorkTask taskByCompanyId = workTaskDao.getSimpleTaskByCompanyId(workTask.getCompanyId());
        if (taskByCompanyId != null) {
                return ResultUtils.fail(TianjiErrorCode.ASSIGNED_ERROR);
        }
        //插入工作任务
        workTaskDao.insert(workTask);

        //插入分派数据到拜访表
        try {
            VisitRecord visitRecord = new VisitRecord();
            visitRecord.setStaffId(workTask.getHandlerId());
            visitRecord.setRecordType(TypeEnums.TYPE_ASSIGN.getType());

            Map<String, Object> source = new HashMap<>();
            GetResponse getResponse = elasticsearchHelper.get(index, type, workTask.getCompanyId());
            source = getResponse.getSource();

            List<Object> objects = new ArrayList<>();
            objects = (List)source.get("loc");

            Map<String, String> map = new HashMap<>();
            map = (HashMap)source.get("information");

            String logo = (String) source.get("logo");

            String location = (String) source.get("location");

            visitRecord.setVisitName(map.get("企业中文名称"));
            visitRecord.setVisitLocation(location);
            //添加经纬度
            visitRecord.setVisitLongitude((Double)objects.get(0));
            visitRecord.setVisitLatitude((Double)objects.get(1));
            visitRecord.setCompanyId(workTask.getCompanyId());
            visitRecord.setImgUrl(logo);
            visitRecord.setStatus(ProjectStatusEnum.WAIT_VISIT.getCode());

            visitRecordMapper.insertSelective(visitRecord);

        } catch (Exception e) {
            log.error("插入数据失败{}", workTask.getCompanyId());
        }


        return ResultUtils.success("success");
    }

    @Override
    public void updateWorkTask(WorkTask workTask) {
        WorkTask existedTask = workTaskDao.getTaskByTaskId(workTask.getId());
        if (existedTask.getIsAccept().equals("0") && workTask.getIsAccept().equals("1")) {
            //招商员确认接收后添加一条记录为待跟进
            StatusLog statusLog = new StatusLog();
            statusLog.setCompanyId(existedTask.getCompanyId());
            statusLog.setStaffId(existedTask.getHandlerId());
            statusLog.setStatus("待跟进");
            statusLog.setFrom(existedTask.getFrom());
            statusLog.setType(0);
            statusLog.setOrigin(existedTask.getOrigin());
            statusLogDao.insert(statusLog);
            workCircleDao.insert(statusLog);
        }
        workTaskDao.updateWorkTask(workTask);
    }

    @Override
    public Map<String, Object> listMyAssignTasks(String userid, Integer page, Integer pageSize) {
        User userDetail = userDao.getUserInfo(userid);
        page = page == null ? 1 : page;
        pageSize = pageSize == null ? 10 : pageSize;
        PageHelper.startPage(page, pageSize);
        List<WorkTask> workTasks = workTaskDao.listMyAssignWorkTasks(userid);
        if (null != workTasks && workTasks.size() > 0) {

            //获取来自量知的工作任务
            List<WorkTask> lzTasks = workTasks.stream()
                    .filter(workTask -> { return workTask.getFrom().equals("量知"); })
                    .collect(Collectors.toList());
            List<Map<String, Object>> lzCompanyDetails = getCompanyDetail(lzTasks);
            for (int i=0; i<lzTasks.size();i++) {
                lzTasks.get(i).setCompany(lzCompanyDetails.get(i));
            }

            //获取工作任务中来自火石的数据
            List<WorkTask> hsTasks = workTasks.stream()
                    .filter(workTask -> { return workTask.getFrom().equals("火石"); })
                    .collect(Collectors.toList());
            List<String> hsIdList = hsTasks.stream().map(WorkTask::getCompanyId)
                    .collect(Collectors.toList());
            List<Map<String, Object>> hsCompanyDetails = new ArrayList<>();

            //获取来自火石的工作任务的公司详情
            if (hsIdList.size() > 0) {
                hsCompanyDetails = getHsCompanyDetail(hsIdList);
            }
            for (int i=0; i<hsTasks.size();i++) {
                hsTasks.get(i).setCompany(hsCompanyDetails.get(i));
            }
        }
        PageInfo<WorkTask> pageInfo = new PageInfo<>(workTasks);
        List<WorkTask> list = pageInfo.getList();
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("total", pageInfo.getTotal());
        resultMap.put("pages", pageInfo.getPages());
        List<Map<String,Object>> resultList = new ArrayList<>();
        resultMap.put("list", resultList);
        for (WorkTask workTask:list) {
            Map<String,Object> taskMap = new HashMap<>();
            List<StatusLog> statuslogs = statusLogDao.listStatusLogByCompanyId(workTask.getCompanyId(), userDetail.getMessageTime());
            taskMap.put("task", workTask);
            taskMap.put("statusLogs", statuslogs);
            resultList.add(taskMap);
        }
        return resultMap;
    }

    @Override
    public WorkTask getNormalNewestLogTask(String staffId) {
        WorkTask workTask = workTaskDao.getNormalNewestLogTask(staffId);
        if (workTask!=null) {
            if (StringUtils.isNotBlank(workTask.getFrom())){
                if (workTask.getFrom().equals("量知")) {
                    Map<String, Object> companyDetail = searchUtil.getCompanyDetail(workTask, appConfig.getEsIndexCompany(), appConfig.getEsTypeCompany());
                    workTask.setCompany(companyDetail);
                } else if (workTask.getFrom().equals("火石")) {
                    Map<String, Object> hsCompanyDetail = new HashMap<>();
                    switch (workTask.getOrigin()) {
                        case "web":
                            hsCompanyDetail = searchUtil.getOneHsCompanyDetail(workTask.getCompanyId());
                            break;
                        case "app":
                            hsCompanyDetail = searchUtil.getOneHsCompanyDetailByApp(workTask.getCompanyId());
                            break;
                        default:
                            log.info("任务来源origin:{}不在范围内", workTask.getOrigin());
                            break;
                }
                    workTask.setCompany(hsCompanyDetail);
                }
            }
        }
        return workTask;
    }

    @Override
    public WorkTask getLeadNewestLogTask(String staffId) {
        WorkTask workTask = workTaskDao.getLeadNewestLogTask(staffId);
        if (workTask!=null) {
            if (StringUtils.isNotBlank(workTask.getFrom())){
                if (workTask.getFrom().equals("量知")) {
                    Map<String, Object> companyDetail = searchUtil.getCompanyDetail(workTask, appConfig.getEsIndexCompany(), appConfig.getEsTypeCompany());
                    workTask.setCompany(companyDetail);
                } else if (workTask.getFrom().equals("火石")) {
                    Map<String, Object> hsCompanyDetail = new HashMap<>();
                    switch (workTask.getOrigin()) {
                        case "web":
                            hsCompanyDetail = searchUtil.getOneHsCompanyDetail(workTask.getCompanyId());
                            break;
                        case "app":
                            hsCompanyDetail = searchUtil.getOneHsCompanyDetailByApp(workTask.getCompanyId());
                            break;
                        default:
                            log.info("任务来源origin:{}不在范围内", workTask.getOrigin());
                            break;
                    }
                    workTask.setCompany(hsCompanyDetail);
                }
            }
        }
        return workTask;
    }


    @Override
    public PageBean listAllNormalTasks(String staffId, Integer isAccept, Integer page, Integer pageSize) {
        page = page == null ? 1 : page;
        pageSize = pageSize == null ? 10 : pageSize;
        PageHelper.startPage(page, pageSize);
        List<WorkTask> workTasks = workTaskDao.listAllNormalTasksWithStatus(staffId, isAccept);
        PageInfo<WorkTask> pageInfo = new PageInfo<>(workTasks);
        PageBean<WorkTask> pageBean = new PageBean<>();
        pageBean.setPages(pageInfo.getPages());
        pageBean.setTotal(pageInfo.getTotal());
        if (null != workTasks && workTasks.size() > 0) {

            //获取来自量知的工作任务
            List<WorkTask> lzTasks = getLzCompanyDetails(workTasks);

            //获取工作任务中来自火石的数据
            List<WorkTask> hsTasks = getHsCompanyDetailsFromWeb(workTasks);

            //获取工作任务中来自火石APP的数据
            List<WorkTask> hsTasksByApp = getHsCompanyDetailsFromApp(workTasks);

            lzTasks.addAll(hsTasks);

            lzTasks.addAll(hsTasksByApp);

            workTasks = lzTasks.stream()
                    .sorted(Comparator.comparing(WorkTask::getCreateTime).reversed())
                    .collect(Collectors.toList());

            for (WorkTask workTask:workTasks) {
               StatusLog statusLog = statusLogDao.getLastestLogByCompanyId(workTask.getCompanyId());
               if(statusLog!=null) {
                   workTask.setStatus(statusLog.getStatus());
               }
            }
        }
        pageBean.setList(workTasks);
        return pageBean;
    }


    @Override
    public PageBean listAllLeadTasks(String staffId, Integer isAccept, Integer page, Integer pageSize) {
        page = page == null ? 1 : page;
        pageSize = pageSize == null ? 10 : pageSize;
        PageHelper.startPage(page, pageSize);
        List<WorkTask> workTasks = workTaskDao.listAllLeadTasksWithStatus(staffId, isAccept);
        PageInfo<WorkTask> pageInfo = new PageInfo<>(workTasks);
        PageBean<WorkTask> pageBean = new PageBean<>();
        pageBean.setPages(pageInfo.getPages());
        pageBean.setTotal(pageInfo.getTotal());
        if (null != workTasks && workTasks.size() > 0) {

            //获取来自量知的工作任务
            List<WorkTask> lzTasks = getLzCompanyDetails(workTasks);

            //获取工作任务中来自火石的数据
            List<WorkTask> hsTasks = getHsCompanyDetailsFromWeb(workTasks);

            //获取工作任务中来自火石APP的数据
            List<WorkTask> hsTasksByApp = getHsCompanyDetailsFromApp(workTasks);

            lzTasks.addAll(hsTasks);

            lzTasks.addAll(hsTasksByApp);

            workTasks = lzTasks.stream()
                    .sorted(Comparator.comparing(WorkTask::getCreateTime).reversed())
                    .collect(Collectors.toList());

            for (WorkTask workTask:workTasks) {
               StatusLog statusLog = statusLogDao.getLastestLogByCompanyId(workTask.getCompanyId());
               if(statusLog!=null) {
                   workTask.setStatus(statusLog.getStatus());
               }
            }
        }
        pageBean.setList(workTasks);
        return pageBean;
    }

    @Override
    public StatusLog getNewestLog(String companyId) {
        StatusLog statusLog = statusLogDao.getNewestLogByCompanyId(companyId);
        return statusLog;
    }

    @Override
    public ResultInfo listNewLeadTasks(String staffId, Integer page, Integer pageSize) {
        Map<String,Object> resp = new HashMap<>();
        List<String> list = new ArrayList<>();

        //关注的所有公司id
        List<String> followed = userDao.listCompanysFollowed(staffId);
        //派发的任务的公司id
        List<String> assginIds = workTaskDao.listAssignTaskCompanyIds(staffId, null);
        list.addAll(followed);
        list.addAll(assginIds);
       // UserDetail userDetail = userDao.getUserDetail(staffId);
        User userDetail = userDao.getUserInfo(staffId);
        if (list.size() <= 0 ) {
            return ResultUtils.success(new HashMap<>());
        }
        page = page == null ? 1 : page;
        pageSize = pageSize == null ? 10 : pageSize;
        PageHelper.startPage(page, pageSize);
        List<String> companyIds = statusLogDao.listStatusLogByCompanyIds(list, userDetail.getIndexNoticeTime());
        page = page == null ? 1 : page;
        pageSize = pageSize == null ? 10 : pageSize;
        PageHelper.startPage(page, pageSize);
        if (companyIds == null || companyIds.size()<=0) {
            return ResultUtils.success(new HashMap<>());
        }
        List<WorkTask> workTasks = workTaskDao.listTasksByCompanyIds(companyIds);
        PageInfo<WorkTask> pageInfo = new PageInfo<>(workTasks);
        resp.put("total", pageInfo.getTotal());
        resp.put("pages", pageInfo.getPages());
        log.info("近期跟进过的公司:{}", companyIds);
        List<Map<String,Object>> resultList = new ArrayList<>();
        if (workTasks!=null&&workTasks.size()>0) {
            List<WorkTask> detailedTasks = new ArrayList<>();
            List<WorkTask> lzCompanyDetails = getLzCompanyDetails(workTasks);
            List<WorkTask> hsCompanyDetails = getHsCompanyDetailsFromWeb(workTasks);
            List<WorkTask> hsCompanyDetailsByApp = getHsCompanyDetailsFromApp(workTasks);
            detailedTasks.addAll(lzCompanyDetails);
            detailedTasks.addAll(hsCompanyDetails);
            detailedTasks.addAll(hsCompanyDetailsByApp);
            for (WorkTask workTask:detailedTasks) {
                Map<String,Object> resultMap = new HashMap<>();
                List<StatusLog> statusLogs = statusLogDao.listStatusLogByCompanyId(workTask.getCompanyId(), null);
                //火石数据待对接
                resultMap.put("task", workTask);
                resultMap.put("statusLog", statusLogs);
                resultList.add(resultMap);
            }
        }
        resp.put("list",resultList);

        return ResultUtils.success(resp);

    }

    @Override
    public ResultInfo listNewNormalTasks(String staffId, Integer page, Integer pageSize) {
        Map<String,Object> resp = new HashMap<>();
        User userDetail = userDao.getUserInfo(staffId);
        Date indexNoticeTime = userDetail.getIndexNoticeTime();
        page = page == null ? 1 : page;
        pageSize = pageSize == null ? 10 : pageSize;
        PageHelper.startPage(page, pageSize);
        List<WorkTask> workTasks = workTaskDao.listNewHandleTasksByTime(staffId,indexNoticeTime);
        PageInfo<WorkTask> pageInfo = new PageInfo<>(workTasks);
        List<WorkTask> detailedTasks = new ArrayList<>();
        if (workTasks != null && workTasks.size() > 0){
            List<WorkTask> lzCompanyDetails = getLzCompanyDetails(workTasks);
            List<WorkTask> hsCompanyDetails = getHsCompanyDetailsFromWeb(workTasks);
            List<WorkTask> hsCompanyDetailsByApp = getHsCompanyDetailsFromApp(workTasks);
            detailedTasks.addAll(lzCompanyDetails);
            detailedTasks.addAll(hsCompanyDetails);
            detailedTasks.addAll(hsCompanyDetailsByApp);
        }
        resp.put("total", pageInfo.getTotal());
        resp.put("pages", pageInfo.getPages());
        resp.put("list", detailedTasks);
        return ResultUtils.success(resp);
    }

    @Override
    public ResultInfo getNormalWorkNum(String staffId) {
        User userDetail = userDao.getUserInfo(staffId);
        Date messageTime = userDetail.getMessageTime();
        Integer count = workTaskDao.countNewTask(staffId, messageTime);
        return ResultUtils.success(count);
    }

    @Override
    public ResultInfo getLeadWorkNum(String staffId) {
        User userDetail = userDao.getUserInfo(staffId);
        Date messageTime = userDetail.getMessageTime();
        Integer count = workTaskDao.getLeadWorkNum(staffId, messageTime);
        return ResultUtils.success(count);
    }

    @Override
    public ResultInfo countNormalMessage(String staffId) {
        ResultInfo resultInfo = eventService.listNewNormalEventsFromLz(staffId, 1, 10);
        Map<String, Object> body = (Map<String, Object>) resultInfo.getBody();
        Long totalEvents = (Long) body.get("total");

        User userDetail = userDao.getUserInfo(staffId);
        Date messageTime = userDetail.getMessageTime();
        List<WorkTask> workTasks = workTaskDao.listNewHandleTasksByTime(staffId,messageTime);
        Integer totalTasks = 0;
        if (workTasks != null) {
            totalTasks = workTasks.size();
        }

        return ResultUtils.success(totalEvents + totalTasks);

    }

    @Override
    public String isRecordable(String companyId) {
        String staffId = workTaskDao.isRecordable(companyId);
        return  staffId;
    }

    @Override
    public ResultInfo getTypeValue(String type) {
        List<String> list = workTaskDao.getTypeValue(type);
        return ResultUtils.success(list);
    }

    @Override
    public List<Map<String, Object>> listAllWorkStationByGroup() {
        Map<String, Object> conditon = new HashMap<>();
        List<Map<String, Object>> list = workTaskDao.listAllWorkStationByGroup(conditon);
        return list;
    }

    @Override
    public List<Map<String, Object>> getNewWorkStationByGroup() {
        Map<String, Object> conditon = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.format(new Date());
        conditon.put("date", dateFormat.format(new Date())+" 00:00:00");
        List<Map<String, Object>> list = workTaskDao.listAllWorkStationByGroup(conditon);
        return list;
    }

    @Override
    public Map<String, List<Map<String, Object>>> getNewaddLogByGroup() {
        Map<String, Object> conditon = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Map<String, List<Map<String, Object>>> result = new LinkedHashMap<>();
        String date = "";
        for (int i =0 ; i <7 ; i++) {
            calendar.add(Calendar.DAY_OF_WEEK,  -6 +i);
            if (i == 0){
                date = dateFormat.format(calendar.getTime());
            }
            result.put(dateFormat.format(calendar.getTime()),new LinkedList<>());
            calendar.setTime(new Date());
        }
        conditon.put("date", date+" 00:00:00");
        List<Map<String,Object>> logList = workTaskDao.getNewaddLogByGroup(conditon);
        List<String> group = workTaskDao.getTypeValue("group");
        for (Map<String, Object> map : logList) {
            if (result.containsKey(map.get("time").toString())) {
                List<Map<String, Object>> groupList = result.get(map.get("time").toString());
                groupList.add(map);
            }
        }
        for (String key : result.keySet()) {
            List<Map<String, Object>> list = result.get(key);
            for (String seg : group) {
                Boolean flag = true;
                for (Map<String, Object> map: list) {
                    if (map.containsValue(seg)) {
                        flag = false;
                        break;
                    }
                }
                if (flag){
                    Map<String,Object> groupMap = new HashMap<>();
                    groupMap.put("group", seg);
                    groupMap.put("time", key);
                    groupMap.put("count", 0);
                    list.add(groupMap);
                }
            }
        }
        return result;
    }

    @Override
    public Map<String, List<Map<String, Object>>> getNewAverageAddLogByGroup() {
        Map<String, Object> conditon = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Map<String, List<Map<String, Object>>> result = new LinkedHashMap<>();
        String date = "";
        for (int i =0 ; i <7 ; i++) {
            calendar.add(Calendar.DAY_OF_WEEK,  -6 +i);
            if (i == 0){
                date = dateFormat.format(calendar.getTime());
            }
            result.put(dateFormat.format(calendar.getTime()),new LinkedList<>());
            calendar.setTime(new Date());
        }
        conditon.put("date", date+" 00:00:00");
        conditon.put("average", 1);
        List<Map<String,Object>> logList = workTaskDao.getNewaddLogByGroup(conditon);
        List<String> group = workTaskDao.getTypeValue("group");
        List<Map<String, Object>> groupNum = userDao.getGroupNum();
        Map<String,Object> numMap = new HashMap<>();
        for (Map<String,Object> map : groupNum) {
            numMap.put(map.get("group").toString(), map.get("count"));
        }

        for (Map<String, Object> map : logList) {
            if (result.containsKey(map.get("time").toString())) {
                List<Map<String, Object>> groupList = result.get(map.get("time").toString());
                Double average = Double.valueOf((long)map.get("count"))/ Double.valueOf((long)numMap.get(map.get("group")));
                BigDecimal b = new BigDecimal(average);
                map.put("average", b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                map.remove("num");
                map.remove("count");
                groupList.add(map);
            }
        }
        for (String key : result.keySet()) {
            List<Map<String, Object>> list = result.get(key);
            for (String seg : group) {
                Boolean flag = true;
                for (Map<String, Object> map: list) {
                    if (map.containsValue(seg)) {
                        flag = false;
                        break;
                    }
                }
                if (flag){
                    Map<String,Object> groupMap = new HashMap<>();
                    groupMap.put("group", seg);
                    groupMap.put("time", key);
                    groupMap.put("average", 0);
                    list.add(groupMap);
                }
            }
        }

        return result;
    }

    @Override
    public ResultInfo countNoramlLoggedTasksToday(String staffId) {
        Integer count = workTaskDao.countNoramlLoggedTasksToday(staffId);
        count = count == null ? 0 : count;
        return ResultUtils.success(count);
    }

    @Override
    public ResultInfo countLeadLoggedTasksToday(String staffId) {
        Integer count = workTaskDao.countLeadLoggedTasksToday(staffId);
        count = count == null ? 0 : count;
        return ResultUtils.success(count);
    }

    private List<Map<String, Object>> getCompanyDetail(List<WorkTask> workTasks) {
        List<String> idList = workTasks.stream()
                                      .map((workTask) -> (workTask.getCompanyId()))
                                      .collect(Collectors.toList());
        if (idList.size() <= 0) {
            return new ArrayList<>();
        }
        String []ids = new String[idList.size()];
        ids  = idList.toArray(ids);
        MultiGetResponse response = elasticsearchHelper.multiGet(appConfig.getEsIndexCompany(), appConfig.getEsTypeCompany(), ids);
        MultiGetItemResponse[] responses = response.getResponses();
        List<Map<String,Object>> list = new ArrayList<>();
        for (MultiGetItemResponse resp:responses) {
            Map<String, Object> sourceAsMap = resp.getResponse().getSourceAsMap();
            if (sourceAsMap!=null) {
                sourceAsMap.put("id",resp.getId());
            }
            list.add(sourceAsMap);
        }
        return list;
    }

    public List<WorkTask> getLzCompanyDetails(List<WorkTask> workTasks) {
        //获取来自量知的工作任务
        List<WorkTask> lzTasks = workTasks.stream()
                .filter(workTask -> { return workTask.getFrom().equals("量知"); })
                .collect(Collectors.toList());

        //获取来自量知的工作任务的公司详情
        List<Map<String, Object>> lzCompanyDetails = getCompanyDetail(lzTasks);
        for (int i=0; i<lzTasks.size();i++) {
            lzTasks.get(i).setCompany(lzCompanyDetails.get(i));
        }
        return lzTasks;
    }

    public List<WorkTask> getHsCompanyDetailsFromWeb(List<WorkTask> workTasks) {
        //获取工作任务中来自火石的数据
        List<WorkTask> hsTasks = workTasks.stream()
                .filter(workTask -> { return workTask.getFrom().equals("火石")&&workTask.getOrigin().equals("web"); })
                .collect(Collectors.toList());
        List<String> hsIdList = hsTasks.stream().map(WorkTask::getCompanyId)
                .collect(Collectors.toList());
        List<Map<String, Object>> hsCompanyDetails = new ArrayList<>();

        //获取来自火石的工作任务的公司详情
        if (hsIdList.size() > 0) {
            hsCompanyDetails = getHsCompanyDetail(hsIdList);
        }
        for (int i=0; i<hsTasks.size();i++) {
            for (Map<String,Object> hsCompanyDetail:hsCompanyDetails) {
                if(hsTasks.get(i).getCompanyId().equals(hsCompanyDetail.get("id").toString())) {
                    hsTasks.get(i).setCompany(hsCompanyDetail);
                }
            }
        }
        return hsTasks;
    }
    public List<WorkTask> getHsCompanyDetailsFromApp(List<WorkTask> workTasks) {
        //获取工作任务中来自火石的数据
        List<WorkTask> hsTasks = workTasks.stream()
                .filter(workTask -> { return workTask.getFrom().equals("火石")&&workTask.getOrigin().equals("app"); })
                .collect(Collectors.toList());
        List<String> hsIdList = hsTasks.stream()
                .filter(distinctByKey(WorkTask::getCompanyId))
                .map(WorkTask::getCompanyId)
                .collect(Collectors.toList());
        List<Map<String, Object>> hsCompanyDetails = new ArrayList<>();

        //获取来自火石的工作任务的公司详情
        if (hsIdList.size() > 0) {
            hsCompanyDetails = searchUtil.listHsCompanyDetailsByApp(hsIdList);
        }

        for (int i=0; i<hsTasks.size();i++) {
            for (Map<String,Object> hsCompanyDetail:hsCompanyDetails) {
                if(hsTasks.get(i).getCompanyId().equals(hsCompanyDetail.get("id").toString())) {
                    hsTasks.get(i).setCompany(hsCompanyDetail);
                }
            }
        }
        return hsTasks;
    }


   private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
