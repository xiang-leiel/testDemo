package com.quantchi.tianji.service.search.service.impl;

import com.quantchi.tianji.service.search.config.AppConfig;
import com.quantchi.tianji.service.search.dao.UserDao;
import com.quantchi.tianji.service.search.dao.WorkCircleDao;
import com.quantchi.tianji.service.search.helper.ElasticsearchHelper;
import com.quantchi.tianji.service.search.helper.SearchUtil;
import com.quantchi.tianji.service.search.model.Order;
import com.quantchi.tianji.service.search.model.WorkCircle;
import com.quantchi.tianji.service.search.model.WorkCircleImpl;
import com.quantchi.tianji.service.search.service.WorkCircleService;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: DeQing-InvestmentEnterprise
 * @author: mf
 * @create: 2019-07-10 15:34
 **/
@Service
public class WorkCircleServiceImpl implements WorkCircleService {

    @Autowired
    private WorkCircleDao workCircleDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private ElasticsearchHelper elasticsearchHelper;

    @Autowired
    private SearchUtil searchUtil;

    @Value("${hs.url}")
    private String hsUrl;


    @Override
    public void save(WorkCircle workCircle) {
        workCircleDao.insert(workCircle);

    }

    @Override
    public Map<String, Object> list(Order order) {
        Map<String,Object> resultMap = new HashMap<>();
        switch (order.getSortBy()) {
            case "createTime":
                resultMap = listByTime(order);
                break;
            case "departId":
                resultMap = listByDepartId(order);
                break;
            case "staffId":
                resultMap = listByStaffId(order);
                break;

        }

        return resultMap;
    }

    private Map<String, Object> listByStaffId(Order order) {
        Integer pageSize = order.getPageSize();
        Integer page = order.getPage();
        //需要10-20条时,各取0-20条,按时间排序,取其中10条
        Integer size = page * pageSize;
        order.setSize(size);
        List<WorkCircleImpl> taskList = workCircleDao.listWorkCirclesFromTaskByStaffId(order);
        List<WorkCircleImpl> recordList = workCircleDao.listWorkCirclesFromRecordByStaffId(order);
        Integer recordCount = workCircleDao.countWorkCirclesFromRecordByStaffId(order);
        Integer taskCount = workCircleDao.countWorkCirclesFromTaskByStaffId(order);
        taskList.addAll(recordList);
        List<WorkCircleImpl> circleList = new ArrayList<>();

        //获取公司名称
        List<WorkCircleImpl> lzList = setCompanyNameFromLz(taskList);
        List<WorkCircleImpl> hsListByWeb = setCompanyNameFromHsByWeb(taskList);
        List<WorkCircleImpl> hsListByApp = setCompanyNameFromHsByApp(taskList);
        List<WorkCircleImpl> list = new ArrayList<>();
        list.addAll(lzList);
        list.addAll(hsListByWeb);
        list.addAll(hsListByApp);
        taskList = list;

        //跳过前面(page-1)*pageSize条,直接取到分页后条数
        if (taskList.size()>pageSize) {
            circleList = taskList.stream()
                    .sorted(Comparator.comparing(WorkCircleImpl::getCreateTime).reversed())
                    .skip((page-1) * pageSize)
                    .limit(order.getPageSize())
                    .collect(Collectors.toList());
        } else {
            circleList = taskList.stream()
                    .sorted(Comparator.comparing(WorkCircleImpl::getCreateTime).reversed())
                    .limit(order.getPageSize())
                    .collect(Collectors.toList());
        }
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("total", recordCount + taskCount);
        resultMap.put("list", circleList);
        return resultMap;
    }

    private Map<String, Object> listByDepartId(Order order) {
        Integer pageSize = order.getPageSize();
        Integer page = order.getPage();
        //需要10-20条时,各取0-20条,按时间排序,取其中10条
        Integer size = page * pageSize;
        order.setSize(size);
        List<WorkCircleImpl> taskList = workCircleDao.listWorkCirclesFromTaskByDepartId(order);
        List<WorkCircleImpl> recordList = workCircleDao.listWorkCirclesFromRecordByDepartId(order);
        Integer recordCount = workCircleDao.countWorkCirclesFromRecordByDepartId(order);
        Integer taskCount = workCircleDao.countWorkCirclesFromTaskByDepartId(order);
        taskList.addAll(recordList);
        List<WorkCircleImpl> circleList = new ArrayList<>();

        //获取公司名称
        List<WorkCircleImpl> lzList = setCompanyNameFromLz(taskList);
        List<WorkCircleImpl> hsListByWeb = setCompanyNameFromHsByWeb(taskList);
        List<WorkCircleImpl> hsListByApp = setCompanyNameFromHsByApp(taskList);
        List<WorkCircleImpl> list = new ArrayList<>();
        list.addAll(lzList);
        list.addAll(hsListByWeb);
        list.addAll(hsListByApp);
        taskList = list;
        if (taskList.size()>pageSize) {
             circleList = taskList.stream()
                    .sorted(Comparator.comparing(WorkCircleImpl::getCreateTime).reversed())
                    .skip((page-1) * pageSize)
                    .limit(order.getPageSize())
                    .collect(Collectors.toList());
        } else {
            circleList = taskList.stream()
                    .sorted(Comparator.comparing(WorkCircleImpl::getCreateTime).reversed())
                    .limit(order.getPageSize())
                    .collect(Collectors.toList());
        }
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("total", taskCount + recordCount);
        resultMap.put("list", circleList);
        return resultMap;
    }

    private Map<String, Object> listByTime(Order order) {
        if (order.getSortBy() == null) {
            order.setSortOrder("desc");
        }
        Integer pageSize = order.getPageSize();
        Integer page = order.getPage();
        order.setPage((page-1)*pageSize);
        List<WorkCircleImpl> taskList = workCircleDao.listWorkCirclesFromTaskByTime(order);
        List<WorkCircleImpl> recordList = workCircleDao.listWorkCirclesFromRecordByTime(order);
        Integer totalCount = workCircleDao.getTotalCount();
        taskList.addAll(recordList);
        List<WorkCircleImpl> circleList;

        //获取公司名称
        List<WorkCircleImpl> lzList = setCompanyNameFromLz(taskList);
        List<WorkCircleImpl> hsListByWeb = setCompanyNameFromHsByWeb(taskList);
        List<WorkCircleImpl> hsListByApp = setCompanyNameFromHsByApp(taskList);
        List<WorkCircleImpl> list = new ArrayList<>();
        list.addAll(lzList);
        list.addAll(hsListByWeb);
        list.addAll(hsListByApp);
        taskList = list;

        //降序排列
        if (order.getSortOrder().equals("desc")) {
             circleList = taskList.stream()
                    .sorted(Comparator.comparing(WorkCircleImpl::getCreateTime).reversed())
                    .limit(order.getPageSize())
                    .collect(Collectors.toList());
        } else {
            //升序排列
             circleList = taskList.stream()
                    .sorted(Comparator.comparing(WorkCircleImpl::getCreateTime))
                    .limit(order.getPageSize())
                    .collect(Collectors.toList());
        }

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("total", totalCount);
        resultMap.put("list", circleList);
        return resultMap;
    }


    private String getUserNameByIds(String ids) {
        List<String> userNames = userDao.getUserName(ids);
        String userName = "";
        for (int i = 0; i< userNames.size(); i ++) {
            if (i!=userNames.size()-1) {
                userName = userName + userNames.get(i) + ",";
            } else {
                userName = userName + userNames.get(i);
            }
        }
        return userName;
    }

    private List<WorkCircleImpl> setCompanyNameFromLz(List<WorkCircleImpl> circleList) {
        List<WorkCircleImpl> lzCircleList = circleList.stream()
                .filter(workCircle -> {
                    return workCircle.getFrom() != null && workCircle.getFrom().equals("量知");
                })
                .collect(Collectors.toList());
        List<String> lzIdList = lzCircleList.stream().map(WorkCircleImpl::getCompanyId).collect(Collectors.toList());
        if (lzIdList!=null&&lzIdList.size()>0) {
            String []ids = new String[lzIdList.size()];
            ids  = lzIdList.toArray(ids);
            MultiGetResponse response = elasticsearchHelper.multiGet(appConfig.getEsIndexCompany(), appConfig.getEsTypeCompany(), ids);
            MultiGetItemResponse[] responses = response.getResponses();
            for (int i = 0; i<responses.length; i++) {
                Map<String, Object> sourceAsMap = responses[i].getResponse().getSourceAsMap();
                if (sourceAsMap!=null&&sourceAsMap.get("name")!=null) {
                    lzCircleList.get(i).setCompanyName(sourceAsMap.get("name").toString());
                }
            }
        }
        return lzCircleList;
    }
    private List<WorkCircleImpl> setCompanyNameFromHsByWeb(List<WorkCircleImpl> circleList) {
        List<WorkCircleImpl> hsCircleList = circleList.stream()
                .filter(workCircle -> {
                    return workCircle.getFrom() != null && workCircle.getFrom().equals("火石") && workCircle.getOrigin().equals("web");
                })
                .collect(Collectors.toList());
        List<String> hsIdList = hsCircleList.stream().map(WorkCircleImpl::getCompanyId).collect(Collectors.toList());
        if (hsIdList!=null&&hsIdList.size()>0) {
            List<Map<String, Object>> hsCompanyDetails = searchUtil.listHsCompanyDetails(hsIdList);
            for (int i=0; i<hsCircleList.size();i++) {
                for (Map<String,Object> hsCompanyDetail:hsCompanyDetails) {
                    if(hsCircleList.get(i).getCompanyId().equals(hsCompanyDetail.get("id").toString())) {
                        hsCircleList.get(i).setCompanyName(hsCompanyDetail.get("name").toString());
                    }
                }
            }
        }
        return hsCircleList;
    }
    private List<WorkCircleImpl> setCompanyNameFromHsByApp(List<WorkCircleImpl> circleList) {
        List<WorkCircleImpl> hsCircleList = circleList.stream()
                .filter(workCircle -> {
                    return workCircle.getFrom() != null && workCircle.getFrom().equals("火石") && workCircle.getOrigin().equals("app");
                })
                .collect(Collectors.toList());
        List<String> hsIdList = hsCircleList.stream().map(WorkCircleImpl::getCompanyId).collect(Collectors.toList());
        if (hsIdList!=null&&hsIdList.size()>0) {
            List<Map<String, Object>> hsCompanyDetails = searchUtil.listHsCompanyDetailsByApp(hsIdList);
            for (int i=0; i<hsCircleList.size();i++) {
                for (Map<String,Object> hsCompanyDetail:hsCompanyDetails) {
                    if(hsCircleList.get(i).getCompanyId().equals(hsCompanyDetail.get("id").toString())) {
                        hsCircleList.get(i).setCompanyName(hsCompanyDetail.get("name").toString());
                    }
                }
            }
        }
        return hsCircleList;
    }
}
