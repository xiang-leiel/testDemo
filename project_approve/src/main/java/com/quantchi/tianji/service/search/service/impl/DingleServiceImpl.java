package com.quantchi.tianji.service.search.service.impl;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.quantchi.tianji.service.search.dao.DepartDao;
import com.quantchi.tianji.service.search.dao.UserDao;
import com.quantchi.tianji.service.search.model.Department;
import com.quantchi.tianji.service.search.model.User;
import com.quantchi.tianji.service.search.service.DingService;
import com.quantchi.tianji.service.search.utils.PasswordEncoder;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @program: DeQing-InvestmentEnterprise
 * @description: 用于获取钉钉数据
 * @author: mf
 * @create: 2019-07-12 16:24
 **/
@Service
public class DingleServiceImpl implements DingService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserDao userDao;

    @Autowired
    private DepartDao departDao;

    @Value("${app.key}")
    private String appKey;

    @Value("${app.secret}")
    private String appSecret;

    @Value("${app.parentDepartId}")
    private String parentDepartId;

    @Value("${app.password}")
    private String password;

    @Override
    public String getToken() throws ApiException {
        logger.info("----------------调用钉钉接口获取token--------------");
        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey(appKey);
        request.setAppsecret(appSecret);
        request.setHttpMethod("GET");
        OapiGettokenResponse response = client.execute(request);
        if (!("ok").equals(response.getErrmsg())) {
            logger.error("【调用钉钉接口异常,errCode:{},errMsg:{}】",response.getErrcode(),response.getErrmsg());
            throw new ApiException(String.valueOf(response.getErrcode()),response.getErrmsg());
        }
        String accessToken = response.getAccessToken();
        logger.info("【获取token:{}】", accessToken);
        return accessToken;
    }

    @Override
    public List<OapiDepartmentListResponse.Department> listDepartments(String parentDepartId, String token) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
        OapiDepartmentListRequest request = new OapiDepartmentListRequest();
        request.setId(parentDepartId);
        request.setHttpMethod("GET");
        OapiDepartmentListResponse response = client.execute(request, token);
        if (!("ok").equals(response.getErrmsg())) {
            logger.error("【调用钉钉接口异常,errCode:{},errMsg:{}】",response.getErrcode(),response.getErrmsg());
            throw new ApiException(String.valueOf(response.getErrcode()),response.getErrmsg());
        }
        List<OapiDepartmentListResponse.Department> departments = response.getDepartment();
        return departments;
    }

    public List<User> listUsersByDepart(String departId, String token) throws ApiException {
        List<User> users = new ArrayList<>();
        OapiUserListbypageResponse response = null;
        do {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/listbypage");
            OapiUserListbypageRequest request = new OapiUserListbypageRequest();
            request.setDepartmentId(new Long(departId));
            request.setOffset(0L);
            request.setSize(100L);
            request.setOrder("custom");
            request.setHttpMethod("GET");
            response = client.execute(request, token);
            if (!("ok").equals(response.getErrmsg())) {
                logger.error("【调用钉钉接口异常,errCode:{},errMsg:{}】",response.getErrcode(),response.getErrmsg());
                throw new ApiException(String.valueOf(response.getErrcode()),response.getErrmsg());
            }
            for (OapiUserListbypageResponse.Userlist userlist:response.getUserlist()) {
                User user = new User(userlist.getUserid(),userlist.getName(),departId, userlist.getMobile(), PasswordEncoder.encode(password));
                logger.info("[当前遍历用户:{}]",user.getName());
                users.add(user);
            }
        } while (response!=null&&response.getHasMore());
        return users;

    }

    @Override
    @Transactional
    public void sychronizeDataToMysql() throws ApiException {
        String token = getToken();
        List<User> allUsers = new ArrayList<>();
        List<OapiDepartmentListResponse.Department> departments = listDepartments(parentDepartId, token);
        logger.info("获取部门列表");
        List<Department> departs = new ArrayList<>();
        for (OapiDepartmentListResponse.Department department:departments) {
            logger.info("[遍历部门:{}]",department.getName());
            Department depart = new Department();
            depart.setId(String.valueOf(department.getId()));
            depart.setParentId(String.valueOf(department.getParentid()));
            depart.setName(department.getName());
            departs.add(depart);
            List<User> users = listUsersByDepart(depart.getId(), token);
            allUsers.addAll(users);
        }
        //对用户数据根据id去重
        List<User> distinctUsers = allUsers.stream().filter(distinctByKey(user -> user.getId())).collect(Collectors.toList());
//        删除部门表中原有数据
        departDao.deleteAll();
        //往部门表中插入新的数据
        departDao.insertList(departs);
        //删除用户表中原有数据
        userDao.deleteAll();
        //往用户表中插入新的数据
        userDao.insertList(distinctUsers);
//        userDao.updateAllUser(distinctUsers);
    }

    @Override
    public String getUserid(String accessToken, String code) throws ApiException {
        logger.info("----------------调用钉钉接口获取用户id--------------");
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getuserinfo");
        OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
        request.setCode(code);
        request.setHttpMethod("GET");
        OapiUserGetuserinfoResponse response = client.execute(request, accessToken);
        if (!("ok").equals(response.getErrmsg())) {
            logger.error("【调用钉钉接口异常,errCode:{},errMsg:{}】",response.getErrcode(),response.getErrmsg());
            throw new ApiException(String.valueOf(response.getErrcode()),response.getErrmsg());
        }
        String userId = response.getUserid();
        return userId;
    }

    @Override
    public OapiUserGetResponse getUserDetail(String accessToken, String staffId) throws ApiException {
        logger.info("----------------调用钉钉接口获取用户详情--------------");
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
        OapiUserGetRequest request = new OapiUserGetRequest();
        request.setUserid(staffId);
        request.setHttpMethod("GET");
        OapiUserGetResponse response = client.execute(request, accessToken);
        if (!("ok").equals(response.getErrmsg())) {
            logger.error("【调用钉钉接口异常,errCode:{},errMsg:{}】",response.getErrcode(),response.getErrmsg());
            throw new ApiException(String.valueOf(response.getErrcode()),response.getErrmsg());
        }
        return response;
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}
