package com.quantchi.tianji.service.search.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.dao.UserDao;
import com.quantchi.tianji.service.search.dao.VisitLogDao;
import com.quantchi.tianji.service.search.exception.TianjiErrorCode;
import com.quantchi.tianji.service.search.model.StatusLog;
import com.quantchi.tianji.service.search.model.VisitLog;
import com.quantchi.tianji.service.search.service.VisitLogService;
import com.quantchi.tianji.service.search.utils.DateUtils;
import com.quantchi.tianji.service.search.utils.ReflectUtils;
import com.quantchi.tianji.service.search.utils.ResultUtils;
import com.quantchi.tianji.service.search.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author whuang
 * @date 2019/7/12
 */
@Service
public class VisitLogServiceImpl implements VisitLogService {

    @Autowired
    private VisitLogDao visitLogDao;

    @Autowired
    private UserDao userDao;

    @Override
    public ResultInfo save(VisitLog visitLog) {

        try{
            visitLogDao.insert(visitLog);
        } catch (Exception e) {
            return ResultUtils.fail(TianjiErrorCode.INSERT_ERROR);
        }

        return ResultUtils.success("success");
    }

    @Override
    public ResultInfo get5LastStatusLogByCompanyId4EveryField(String companyId) {
        JSONObject result = new JSONObject();
        // 所有字段列表
        String field = "feedback";
        List<VisitLog> statusLogs = visitLogDao.list5LastByCompanyIdAndField(companyId, "feedback");
        List<JSONObject> fieldHistory = new ArrayList<>(); // 每一个字段的历史
        if (statusLogs != null) {
            for (VisitLog visitLog : statusLogs) {
                JSONObject status = new JSONObject();
                String staffId = visitLog.getStaffId();
                List<String> userNames = userDao.getUserName(staffId);
                String userName = null;
                if (userNames != null && userNames.size() > 0) {
                    userName = userNames.get(0);
                }
                status.put("userName", userName);
                //时间
                String time = null;
                try {
                    time = DateUtils.format2yyyyMMdd(visitLog.getCreateTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                status.put("time", time);
                //获取该属性值
                status.put(field, visitLog.getFeedback());
                fieldHistory.add(status);
            }
        }
        result.put("list",fieldHistory);
        return ResultUtils.success(result);
    }

    @Override
    public ResultInfo getLast(String staffId, String companyId) {
        VisitLog lastVisitLog = visitLogDao.getLast(null, companyId);
        return ResultUtils.success(lastVisitLog);
    }
}
