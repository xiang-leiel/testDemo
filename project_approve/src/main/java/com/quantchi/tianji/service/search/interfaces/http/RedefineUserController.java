package com.quantchi.tianji.service.search.interfaces.http;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.enums.ErrCode;
import com.quantchi.tianji.service.search.model.ReceiverParam;
import com.quantchi.tianji.service.search.model.vo.EvaluateParam;
import com.quantchi.tianji.service.search.model.vo.PreferenceParam;
import com.quantchi.tianji.service.search.model.vo.ProjectQueryParam;
import com.quantchi.tianji.service.search.model.vo.sign.JudgeParam;
import com.quantchi.tianji.service.search.model.vo.sign.ProjectRecordParam;
import com.quantchi.tianji.service.search.service.project.ProjectManageService;
import com.quantchi.tianji.service.search.service.project.ReceiverManageService;
import com.quantchi.tianji.service.search.utils.ResultUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description 
 * @author leiel
 * @Date 2019/12/16 2:29 PM
 */
@RestController
@RequestMapping("/userNew")
public class RedefineUserController {

    @Resource
    private ProjectManageService projectManageService;

    @Resource
    private ReceiverManageService receiverManageService;

    /**
     * 偏好设置
     * @return
     */
    @PostMapping("/preference")
    public ResultInfo preferenceSet(@RequestBody PreferenceParam preferenceParam) {

        ResultInfo resultInfo = projectManageService.preferenceSet(preferenceParam);

        return resultInfo;
    }

    /**
     * 偏好设置查询
     * @return
     */
    @GetMapping("/queryPreference")
    public ResultInfo queryPreference(String staffId) {

        ResultInfo resultInfo = projectManageService.queryPreference(staffId);

        return resultInfo;
    }

    /**
     * 搜索我的项目
     * @return
     */
    @GetMapping("/searchProject")
    public ResultInfo searchProject(String staffId, String visitName, Integer type, Integer page, Integer pageSize) {

        ResultInfo resultInfo = projectManageService.searchProject(staffId, visitName, type, page, pageSize);

        return resultInfo;
    }

    /**
     * 获取当前用户的所有数据
     * @param type 0:获取所有信息  1:待拜访 2:待研判  3:待落地  4:未通过/叫停
     * @return
     */
    @GetMapping("/getProjectInfo")
    public ResultInfo getProjectAll(String staffId, Integer type, Integer page, Integer pageSize) {

        ProjectQueryParam projectQueryParam = new ProjectQueryParam(staffId, type, page, pageSize);

        ResultInfo resultInfo = projectManageService.getProjectAll(projectQueryParam);

        return resultInfo;
    }

    /**
     * 签到 同时查看评价和记录是否完成  将签到记录插入到签到记录表
     * @param
     * @return
     */
    @GetMapping("/sign")
    public ResultInfo sign(String staffId, Long visitId) {

        ResultInfo resultInfo = null;
        //更新签到表数据
        //ResultInfo resultInfo = projectManageService.updateSignDataAddi(staffId, visitId);

        return resultInfo;
    }

     /**
     * 查看当前项目对应的接待人
     * @return
     */
    @GetMapping("/queryReceiverByVisitId")
    public ResultInfo queryReceiverByVisitId(Long visitId) {

        ResultInfo resultInfo = receiverManageService.queryReceiverByVisit(visitId);

        return resultInfo;
    }

    /**
     * 查询接待人详细信息
     * @return
     */
    @GetMapping("/queryReceiver")
    public ResultInfo queryReceiver(Long receiverId) {

        //根据项目名关联项目记录表
        ResultInfo resultInfo = receiverManageService.queryReceiver(receiverId);

        return resultInfo;
    }

    /**
     * 接待人编辑
     * @return
     */
    @PostMapping("/editReceiver")
    public ResultInfo editReceiver(@RequestBody ReceiverParam receiverParam) {

        //根据姓名判断接待人是否已存在，存在即更新否则为新增
        ResultInfo resultInfo = receiverManageService.editReceiver(receiverParam);

        return resultInfo;
    }

    /**
     * 评价接待人 同时查看签到和记录是否完成
     * @return
     */
    @PostMapping("/evaluateReceiver")
    public ResultInfo evaluateReceiver(@RequestBody EvaluateParam evaluateParam) {

        ResultInfo resultInfo = receiverManageService.evaluateReceiver(evaluateParam);

        return resultInfo;
    }

    /**
     * 接待人评价查看
     * @return
     */
    @GetMapping("/queryReceiverEvaluate")
    public ResultInfo queryReceiverEvaluate(Long receiverId) {

        ResultInfo resultInfo = receiverManageService.queryReceiverEvaluate(receiverId);

        return resultInfo;
    }

    /**
     * 项目记录查看
     * @return
     */
    @GetMapping("/queryProjectRecord")
    public ResultInfo queryProjectRecord(Long visitId) {

        ResultInfo resultInfo = projectManageService.queryProjectRecord(visitId);

        return resultInfo;
    }

    /**
     * 项目记录新增 同时查看签到和评价是否完成
     * @return
     */
    @PostMapping("/addProjectRecord")
    public ResultInfo addProjectRecord(@RequestBody ProjectRecordParam projectRecordParam) {

        ResultInfo resultInfo = projectManageService.saveProjectRecord(projectRecordParam);

        return resultInfo;
    }

    /**
     * 研判处理
     * @return
     */
    @PostMapping("/judgeProject")
    public ResultInfo judgeProject(@RequestBody JudgeParam judgeParam) {

        ResultInfo resultInfo = projectManageService.judgeProject(judgeParam);

        return resultInfo;
    }

    /**
     * 落地处理
     * @return
     */
    @GetMapping("/landProject")
    public ResultInfo addProjectRecord(Long visitId) {

        return null;
    }


}
