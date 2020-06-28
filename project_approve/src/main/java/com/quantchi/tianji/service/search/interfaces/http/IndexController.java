package com.quantchi.tianji.service.search.interfaces.http;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.enums.TypeEnums;
import com.quantchi.tianji.service.search.model.Event;
import com.quantchi.tianji.service.search.model.WorkTask;
import com.quantchi.tianji.service.search.service.*;
import com.quantchi.tianji.service.search.utils.FileHandleUtil;
import com.quantchi.tianji.service.search.utils.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: DeQing-InvestmentEnterprise
 * @author: mf
 * @create: 2019-07-09 14:30
 **/
@RestController
@RequestMapping("/index")
public class IndexController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WorkTaskService workTaskService;

    @Autowired
    private WorkCircleService workCircleService;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userSerivce;

    @Autowired
    private ChannelSearchService channelSearchService;

    @PostMapping(value = "/worktask/assign", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResultInfo assign(@RequestBody WorkTask workTask) {
        logger.info("获取参数,handlerId:{},staffId:{}", workTask.getHandlerId(),workTask.getStaffId());
        ResultInfo resultInfo = workTaskService.assign(workTask);
        if (!((Integer)resultInfo.getHeader().get("code")).equals(200)) {
            return resultInfo;
        }
        workTask.setType(TypeEnums.TYPE_ASSIGN.getType());
        workCircleService.save(workTask);

        return ResultUtils.success("success");
    }


    @RequestMapping("/upload/picture")
    public ResultInfo uploadFile(MultipartFile file) {
        String picPath = "";
        try {
          picPath =  FileHandleUtil.upload(file.getInputStream(), "image/", file.getOriginalFilename());
          logger.info("图片存储成功,存储路径为:{}", picPath);
        } catch (Exception e) {
           logger.error("上传文件出错,{}", e.getMessage());
        }
        return ResultUtils.success(picPath);
    }



    /**
     * 资讯搜索
     * @param event
     * @return
     */
    @PostMapping("/event/search")
    public ResultInfo searchCompany(@RequestBody Event event) {
        ResultInfo resultInfo = eventService.search(event);
        return resultInfo;
    }

    /**
     * 招商员的新增资讯
     * @param staffId
     * @return
     */
    @GetMapping("/event/listNewNormalEvents")
    public ResultInfo listNewEvents(String staffId, Integer page, Integer pageSize) {
        ResultInfo resultInfo = eventService.listNewNormalEventsFromLz(staffId, page, pageSize);
        return resultInfo;
    }

    /**
     * 生物医药中招商员的新增资讯
     * @param staffId
     * @return
     */
    @GetMapping("/event/listNewNormalEventsFromHs")
    public ResultInfo listNewEventsFromHs(String staffId, Integer page, Integer pageSize) {
        ResultInfo resultInfo = eventService.listNewNormalEventsFromHs(staffId, page, pageSize);
        return resultInfo;
    }

    /**
     * 生物医药中招商领导的新增资讯
     * @param staffId
     * @return
     */
    @GetMapping("/event/listNewLeadEventsFromHs")
    public ResultInfo listNewLeadEventsFromHs(String staffId, Integer page, Integer pageSize) {
        ResultInfo resultInfo = eventService.listNewLeadEventsFromHs(staffId, page, pageSize);
        return resultInfo;
    }

    /**
     * 招商领导的新增资讯
     * @param staffId
     * @return
     */
    @GetMapping("/event/listNewLeadEvents")
    public ResultInfo listNewLeadEvents(String staffId, Integer page, Integer pageSize) {
        ResultInfo resultInfo = eventService.listNewLeadEventsFromLz(staffId, page, pageSize);
        return resultInfo;
    }

    /**
     * 招商领导的新增任务跟进
     * @param staffId
     * @return
     */
    @GetMapping("/listNewLeadTasks")
    public ResultInfo listNewLeadTasks(String staffId, Integer page, Integer pageSize) {
        ResultInfo resultInfo = workTaskService.listNewLeadTasks(staffId, page, pageSize);
        return resultInfo;
    }

    /**
     * 招商员的新增任务提醒
     * @param staffId
     * @return
     */
    @GetMapping("/listNewNormalTasks")
    public ResultInfo listNewNormalTasks(String staffId, Integer page, Integer pageSize) {
        ResultInfo resultInfo = workTaskService.listNewNormalTasks(staffId, page, pageSize);
        return resultInfo;
    }

    /**
     * 保存用户定位信息
     * @param staffId
     * @return
     */
    @GetMapping("/user/saveLocation")
    public ResultInfo saveUserLocation(String staffId, Double latitude, Double longitude) {
        userSerivce.saveUserLocation(staffId, latitude, longitude);
        return ResultUtils.success("success");
    }

    /**
     * 根据位置及分类获取会议信息
     * @param city 城市：全国，北京，上海，深圳，德清
     * @param category 分类：首页，地理信息，人工智能，生物医药
     * @return
     */
    @GetMapping("/getMeetingInfo")
    public ResultInfo getMeetingInfo(String city, String category){
        Map<String, String> conditionMap = new HashMap<String, String>();
        conditionMap.put("city", city);
        conditionMap.put("category", category);
        ResultInfo resultInfo = channelSearchService.listMeetingInfoByCondition(conditionMap);
        return resultInfo;
    }

    /**
     * 更新统计时间
     * @param staffId
     * @return
     */
    @GetMapping("/updateTime")
    public ResultInfo updateTime(String staffId, String time) {
        userSerivce.updateTime(staffId,time);
        return ResultUtils.success("success");
    }

    /**
     * 获取招商员工作台通知数量
     * @param staffId
     * @return
     */
    @GetMapping("/countNormalWork")
    public ResultInfo countNormal(String staffId) {
        ResultInfo resultInfo = workTaskService.getNormalWorkNum(staffId);
        return resultInfo;
    }
    /**
     * 获取招商领导工作台通知数量
     * @param staffId
     * @return
     */
    @GetMapping("/countLeadWork")
    public ResultInfo countLeadWork(String staffId) {
        ResultInfo resultInfo = workTaskService.getLeadWorkNum(staffId);
        return resultInfo;
    }

    /**
     * 根据类型获取类型值
     * @param type 类型：city城市；category分类；group组别
     * @return
     */
    @GetMapping("/getTypeValue")
    public ResultInfo getTypeValue(String type) {
        ResultInfo resultInfo = workTaskService.getTypeValue(type);
        return resultInfo;
    }


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
