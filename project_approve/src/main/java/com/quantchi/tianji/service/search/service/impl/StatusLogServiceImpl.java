package com.quantchi.tianji.service.search.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.config.AppConfig;
import com.quantchi.tianji.service.search.dao.StatusLogDao;
import com.quantchi.tianji.service.search.dao.UserDao;
import com.quantchi.tianji.service.search.dao.WorkCircleDao;
import com.quantchi.tianji.service.search.dao.WorkTaskDao;
import com.quantchi.tianji.service.search.exception.TianjiErrorCode;
import com.quantchi.tianji.service.search.helper.ElasticsearchHelper;
import com.quantchi.tianji.service.search.model.StatusLog;
import com.quantchi.tianji.service.search.model.WorkTask;
import com.quantchi.tianji.service.search.service.StatusLogService;
import com.quantchi.tianji.service.search.utils.DateUtils;
import com.quantchi.tianji.service.search.utils.ReflectUtils;
import com.quantchi.tianji.service.search.utils.ResultUtils;
import com.quantchi.tianji.service.search.utils.StringUtils;
import org.elasticsearch.action.get.GetResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author whuang
 * @date 2019/7/11
 */
@Service
public class StatusLogServiceImpl implements StatusLogService {

    @Autowired
    private StatusLogDao statusLogDao;

    @Autowired
    private ElasticsearchHelper elasticsearchHelper;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private UserDao userDao;

    @Autowired
    private WorkCircleDao workCircleDao;

    @Autowired
    private WorkTaskDao workTaskDao;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    @Transactional
    public ResultInfo save(StatusLog statusLog) {
        try{
            WorkTask existedTask = workTaskDao.getTaskByCompanyId(statusLog.getCompanyId());
            if (existedTask == null) {
                return ResultUtils.fail(TianjiErrorCode.NOT_ASSIGNED_ERROR);
            }
            statusLog.setOrigin(existedTask.getOrigin());
            statusLog.setFrom(existedTask.getFrom());
            statusLogDao.insert(statusLog);
            //插入到工作圈表中
            statusLog.setType(0);
            workCircleDao.insert(statusLog);
        } catch (Exception e) {
            logger.error("--------插入statusLog异常------,statusLog:{},异常信息:{}", statusLog, e.getMessage());
            return ResultUtils.fail(TianjiErrorCode.INSERT_ERROR);
        }
        return ResultUtils.success("success");
    }

    @Override
    public StatusLog getLastStatusLogByStaffIdAndCompanyId(String staffId, String companyId) {
        return statusLogDao.selectLastByStaffIdAndCompanyId(staffId,companyId);
    }

    @Override
    public List<StatusLog> listStatusLogByStaffIdAndCompanyId(String staffId, String companyId) {
        return statusLogDao.listByStaffIdAndCompanyId(staffId,companyId);
    }

    @Override
    public ResultInfo listMyLoggedCompany(String staffId,Integer page, Integer pageSize) {
        List companyList = new ArrayList();
        page = page == null ? 1 : page;
        pageSize = pageSize == null ? 10 : pageSize;
        PageHelper.startPage(page, pageSize);
        List<StatusLog> companys = statusLogDao.listMyLoggedCompany(staffId);
        PageInfo<StatusLog> pageInfo = new PageInfo<>(companys);
        for (StatusLog company : companys) {
            GetResponse getResponse = elasticsearchHelper.get(appConfig.getEsIndexCompany(), appConfig.getEsTypeCompany(), company.getCompanyId());
            if(getResponse.isExists()) {
                JSONObject hit = new JSONObject();
                String companyId = getResponse.getId();
                Map<String, Object> source = getResponse.getSource();
                hit.put("_id",companyId);
                hit.put("_source",source);
                //查询插入用户对接企业状态
                StatusLog lastStatusLog = getLastStatusLogByStaffIdAndCompanyId(staffId, companyId);
                if(lastStatusLog != null) {
                    String status = lastStatusLog.getStatus();
                    source.put("status",status);
                }
                companyList.add(hit);
            }
        }
        JSONObject result = new JSONObject();
        result.put("total",pageInfo.getTotal());
        result.put("hits",companyList);
        return ResultUtils.success(result);
    }



    @Override
    public ResultInfo get5LastStatusLogByCompanyId4EveryField(String companyId) {

        JSONObject result = new JSONObject();

        // 所有字段列表
        String fields = "staff_id, company_id, intention_status, intention_move_time,status,need_land," +
                "need_office, need_money, need_condition, office_number, office_area, employee_number," +
                "main_product, risk";
        // 循环字段列表
        for (String field : fields.split(",")) {

            List<JSONObject> fieldHistory = new ArrayList<>(); // 每一个字段的历史

            field = field.trim();

            String camelCaseField = StringUtils.underscoreToCamelCase(field);

            List<StatusLog> statusLogs = statusLogDao.list5LastByCompanyIdAndField(companyId, field);
            // 获取记录
            if(statusLogs != null) {
                for (StatusLog statusLog : statusLogs) {
                    JSONObject status = new JSONObject();
                    String staffId = statusLog.getStaffId();
                    List<String> userNames = userDao.getUserName(staffId);
                    String userName = null;
                    if(userNames != null && userNames.size() > 0) {
                        userName = userNames.get(0);
                    }
                    status.put("userName",userName);
                    //时间
                    String time = null;
                    try {
                        time = DateUtils.format2yyyyMMdd(statusLog.getCreateTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    status.put("time",time);
                    //获取该属性值
                    Object value = ReflectUtils.getFieldValueByFieldName(camelCaseField, statusLog);
                    status.put("value",value);
                    fieldHistory.add(status);
                }
                // 字段改成驼峰的
                result.put(camelCaseField,fieldHistory);
            }
        }
        return ResultUtils.success(result);
    }


    @Override
    public List<Map<String, Object>> recordRank(String time) {
        Date date = null;
        if (time!=null) {
            switch (time) {
                case "month":
                    date = getTimesMonthmorning();
                    break;
                case "season":
                    date = getCurrentQuarterStartTime();
                    break;
                case "year":
                    date = getCurrentYearStartTime();
                    break;
                default:
                    break;
            }
        }
        List<Map<String, Object>> maps = statusLogDao.recordRand(date);
        return maps;
    }



    public Date getTimesMonthmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    public Date getCurrentQuarterStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 4);
            else if (currentMonth >= 10 && currentMonth <= 12)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    public Date getCurrentYearStartTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.YEAR));
        return cal.getTime();
    }

    @Override
    public ResultInfo getLast(String staffId, String companyId) {
        StatusLog lastStatusLog = statusLogDao.getLast(null, companyId);
        return ResultUtils.success(lastStatusLog);
    }

    /**
     * 上传文件到服务器
     * @param file
     * @param filePath
     * @param fileName
     * @throws IOException
     */
    private void fileUpload(byte[] file,String filePath,String fileName) throws IOException {
        //目标目录
        File targetfile = new File(filePath);
        if(!targetfile.exists()) {
            targetfile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+"/"+fileName);
        out.write(file);
        out.flush();
        out.close();
    }

}
