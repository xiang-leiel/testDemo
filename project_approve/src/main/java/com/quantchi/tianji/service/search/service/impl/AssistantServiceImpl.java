package com.quantchi.tianji.service.search.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.quantchi.tianji.service.search.dao.AssistantDao;
import com.quantchi.tianji.service.search.dao.UserDao;
import com.quantchi.tianji.service.search.model.PageBean;
import com.quantchi.tianji.service.search.model.UserDetail;
import com.quantchi.tianji.service.search.service.AssistantService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AssistantServiceImpl implements AssistantService {

    @Autowired
    private AssistantDao assistantDao;
    @Autowired
    private UserDao userDao;

    @Value("${pic.url}")
    private String picUrl;
    @Override
    public List<Map<String, Object>> getClassifyInfo(String name) {
        List<Map<String, Object>> list = new LinkedList<>();
        Map<String, String> condition = new HashMap<>();
        condition.put("type", "classify");
        condition.put("value", name);
        if (name != null && !"".equals(name)){
            list = assistantDao.getClassifyDetailInfo(condition);
        }else {
            list =assistantDao.getClassifyInfo("classify");
        }

        return list;
    }

    @Override
    public List<String> getGroupInfo() {
        List<String> list = userDao.getGroupInfo();
        list.add("全部组长");
        list.add("所有人");
        return list;
    }

    @Override
    public void addAssistantInfo(Map<String, Object> condition) {
        condition.put("createTime", new Date());
        assistantDao.addAssistantInfo(condition);
    }

    @Override
    public PageBean<Map<String, Object>> getAssistantInfo(String classify, String userId, Integer from, Integer size) {
        UserDetail userDetail = userDao.getUserDetailInfoByUserId(userId);
        Map<String, Object> condition = new HashMap<>();
        List<String> groupList = new ArrayList<>();
        if (userDetail.getRoleName().contains("领导")){

        }else {
            if (userDetail.getJob() == 1) {
                groupList.add("全部组长");
            }
            groupList.add("所有人");
            groupList.add(userDetail.getGroup());
            condition.put("groupList", groupList);
        }
        String[] classifyArry = classify.split(",");
        condition.put("classify", classifyArry);
        from = from == null ? 1 : from;
        size = size == null ? 10 : size;
        PageHelper.startPage(from, size);
        List<Map<String, Object>> list = assistantDao.getAssistantInfo(condition);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        PageBean<Map<String, Object>> pageBean = new PageBean<>();
        BeanUtils.copyProperties(pageInfo, pageBean);
        return pageBean;
    }

    @Override
    public void addAssistantCommentInfo(Map<String, Object> condition) {
        condition.put("createTime", new Date());
        assistantDao.addAssistantCommentInfo(condition);
    }

    @Override
    public PageBean<Map<String, Object>> getAssistantCommentInfo(Integer assistantId, Integer from, Integer size) {
        from = from == null ? 1 : from;
        size = size == null ? 10 : size;
        PageHelper.startPage(from, size);
        Map<String, Object> condition = new HashMap<>();
        condition.put("assistantId", assistantId);
        condition.put("picUrl", picUrl);
        List<Map<String, Object>> list = assistantDao.getAssistantCommentInfo(condition);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        PageBean<Map<String, Object>> pageBean = new PageBean<>();
        BeanUtils.copyProperties(pageInfo, pageBean);
        return pageBean;
    }

    @Override
    public void addAssistantAgreeInfo(Map<String, Object> condition) {
        condition.put("createTime", new Date());
        assistantDao.addAssistantAgreeInfo(condition);
    }

    @Override
    public int getAssistantAgreeInfoByCondition(Map<String, Object> condition) {
        int flag = assistantDao.getAssistantAgreeInfoByCondition(condition);
        return flag;
    }
}
