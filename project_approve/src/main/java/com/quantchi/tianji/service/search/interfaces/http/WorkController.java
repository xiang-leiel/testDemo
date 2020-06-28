package com.quantchi.tianji.service.search.interfaces.http;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.enums.ErrCode;
import com.quantchi.tianji.service.search.model.PageBean;
import com.quantchi.tianji.service.search.model.StatusLog;
import com.quantchi.tianji.service.search.model.WorkTask;
import com.quantchi.tianji.service.search.model.database.BusinessCardDO;
import com.quantchi.tianji.service.search.service.BusinessCardService;
import com.quantchi.tianji.service.search.service.WorkTaskService;
import com.quantchi.tianji.service.search.utils.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @program: DeQing-InvestmentEnterprise
 * @author: mf
 * @create: 2019-07-09 09:53
 **/

@RestController
@RequestMapping("/work")
public class WorkController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WorkTaskService workTaskService;

    @Autowired
    private BusinessCardService businessCardService;

    @GetMapping(value = "/welcome", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public String welcome() {
        return "welcome";
    }

    /**
     * 指派给我的工作任务列表
     */
    @GetMapping(value = "/worktask/list", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResultInfo<Map<String,Object>> listWorkTasks(String staffId, Long intervalTime, Integer isAccept,
                                                        Integer page,
                                                        Integer pageSize) {
        logger.info("isAccept:{}", isAccept);
        Map<String, Object> resultMap = workTaskService.listWorkTasksByUserid(staffId, intervalTime, isAccept, page,
                pageSize);
        return ResultUtils.success(resultMap);
    }

    @PostMapping(value = "/updateWorktask")
    public ResultInfo<String> updateWorkTask(@RequestBody WorkTask workTask) {
        if (workTask.getIsAccept() == null && workTask.getIsRead()== null ) {
            return ResultUtils.success("success");
        }
        workTaskService.updateWorkTask(workTask);
        return ResultUtils.success("success");
    }

    @PostMapping(value = "/card/upload", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResultInfo recognizeBusinessCard(MultipartFile file, String staffId, int leaderFlag) {
        BusinessCardDO businessCardDO = new BusinessCardDO();
        try {
            businessCardDO = businessCardService.recognizeCardAndSave(file, staffId, leaderFlag);
        }catch(Exception e) {
            if(e.getCause() instanceof MySQLIntegrityConstraintViolationException)
                return ResultUtils.fail(10087, "已上传过该用户的公司名片");
        }
        return ResultUtils.success(businessCardDO);
    }

    /*@GetMapping(value = "/card/list", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResultInfo<PageBean<BusinessCardDO>> listCard(Integer page, Integer pageSize, String staffId, String city,
                                                         String category, String company) {
        PageBean<BusinessCardDO> pageBean = businessCardService.list(page, pageSize, staffId, city);
        return ResultUtils.success(pageBean);
    }*/

    /**
     * 我指派的工作任务列表
     * @return
     */
    @GetMapping(value = "/worktask/listmytasks")
    public ResultInfo<Map<String,Object>> listMyAssginTask(String staffId, Integer page, Integer pageSize) {
        Map<String, Object> resultMap = workTaskService.listMyAssignTasks(staffId, page, pageSize);
        return ResultUtils.success(resultMap);
    }

    @GetMapping(value = "/card/getone", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResultInfo getOneCard(Integer id) {
        BusinessCardDO businessCardDO = businessCardService.getOne(id);
        return ResultUtils.success(businessCardDO);
    }

    /**
     * 招商员最新跟进的一条任务
     */
    @GetMapping(value = "/getNormalNewestLogTask")
    public ResultInfo getNormalNewestLogTask(String staffId) {
        WorkTask workTask = workTaskService.getNormalNewestLogTask(staffId);
        return ResultUtils.success(workTask);
    }

    /**
     * 招商领导的最近跟进任务
     */
    @GetMapping(value = "/getLeadNewestLogTask")
    public ResultInfo getLeadNewestLogTask(String staffId) {
        WorkTask workTask = workTaskService.getLeadNewestLogTask(staffId);
        return ResultUtils.success(workTask);
    }

    /**
     * 招商员工作台全部任务
     */
    @GetMapping(value = "/listAllNormalTasks")
    public ResultInfo listAllNormalTasks(String staffId,Integer isAccept, Integer page, Integer pageSize) {
        PageBean pageBean = workTaskService.listAllNormalTasks(staffId, isAccept, page, pageSize);
        return ResultUtils.success(pageBean);
    }

    /**
     * 招商领导工作台全部任务
     */
    @GetMapping(value = "/listAllLeadTasks")
    public ResultInfo listAllLeadTasks(String staffId,Integer isAccept, Integer page, Integer pageSize) {
        PageBean pageBean = workTaskService.listAllLeadTasks(staffId, isAccept, page, pageSize);
        return ResultUtils.success(pageBean);
    }


    @GetMapping(value = "/getNewestLog")
    public ResultInfo getNewestLog(String companyId) {
        StatusLog statusLog = workTaskService.getNewestLog(companyId);
        return ResultUtils.success(statusLog);
    }


    /**
     * 工作台消息数量
     */
    @GetMapping(value = "/countNormalMessage")
    public ResultInfo countNormalMessage(String staffId) {
        ResultInfo resultInfo = workTaskService.countNormalMessage(staffId);
        return ResultUtils.success(resultInfo);
    }

    /**
     * 获取招商总目标数
     * @return
     */
    @CrossOrigin
    @GetMapping(value = "/getWorkStationByGroup")
    public ResultInfo getWorkStationByGroup() {
        List<Map<String, Object>> list = workTaskService.listAllWorkStationByGroup();
        return ResultUtils.success(list);
    }

    /**
     * 获取招商新增目标数
     * @return
     */
    @CrossOrigin
    @GetMapping(value = "/getNewWorkStationByGroup")
    public ResultInfo getNewWorkStationByGroup() {
        List<Map<String, Object>> list = workTaskService.getNewWorkStationByGroup();
        return ResultUtils.success(list);
    }
    @CrossOrigin
    @GetMapping(value = "/getNewAddLogByGroup")
    public ResultInfo getNewAddLogByGroup(){
        Map<String, List<Map<String, Object>>> map = workTaskService.getNewaddLogByGroup();
        return ResultUtils.success(map);
    }
    @CrossOrigin
    @GetMapping(value = "/getNewAverageAddLogByGroup")
    public ResultInfo getNewAverageAddLogByGroup(){
        Map<String, List<Map<String, Object>>> map = workTaskService.getNewAverageAddLogByGroup();
        return ResultUtils.success(map);
    }

    /**
     * 招商员今日跟进任务数
     */
    @GetMapping(value = "/countNormalLoggedTasksToday")
    public ResultInfo countNoramlLoggedTasksToday(String staffId) {
        ResultInfo resultInfo = workTaskService.countNoramlLoggedTasksToday(staffId);
        return resultInfo;
    }

    /**
     * 招商领导今日跟进任务数
     */
    @GetMapping(value = "/countLeadLoggedTasksToday")
    public ResultInfo countLeadLoggedTasksToday(String staffId) {
        ResultInfo resultInfo = workTaskService.countLeadLoggedTasksToday(staffId);
        return resultInfo;
    }

}
