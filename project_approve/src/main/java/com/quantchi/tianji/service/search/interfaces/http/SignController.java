package com.quantchi.tianji.service.search.interfaces.http;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.model.vo.sign.VisitRecordParam;
import com.quantchi.tianji.service.search.service.sign.SignService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description 
 * @author leiel
 * @Date 2019/12/9 2:25 PM
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/sign")
public class SignController {

    @Resource
    private SignService signService;

    /**
     * 获取当前招商人员基本信息
     * @return
     */
    @GetMapping("/getUserInfo")
    public ResultInfo getUserInfo(String staffId) {

        ResultInfo resultInfo = signService.getUserInfo(staffId);

        return resultInfo;
    }

    /**
     * 首页签到
     * @param staffId 钉钉用户id
     * @param visitAte 拜访玮度
     * @param visitLte 拜访经度
     * @return
     */
    @GetMapping("/user")
    public ResultInfo sign(String staffId, Long projectId, String visitLocation, Double visitAte, Double visitLte) {

        //将用户签到信息落库并返回签到次数及签到数据
        ResultInfo resultInfo = signService.saveUserSignRecord(staffId, projectId, visitLocation, visitAte, visitLte);

        return resultInfo;
    }

    /**
     * 当前招商人员添加未拜访记录
     * @return
     */
    @PostMapping("/addVisitRecord")
    public ResultInfo addVisitRecord(@RequestBody VisitRecordParam visitRecordParam) {

        ResultInfo resultInfo = signService.addVisitRecord(visitRecordParam);

        return resultInfo;
    }

    /**
     * 查询当前招商人员添加未拜访记录
     * @return
     */
    @GetMapping("/queryVisitData")
    public ResultInfo queryVisitData(String visitId) {

        ResultInfo resultInfo = signService.queryVisitData(visitId);

        return resultInfo;
    }

    /**
     * 获取当前招商人员已拜访和未拜访的数据
     * @return
     */
    @GetMapping("/queryVisitRecord")
    public ResultInfo queryVisitRecord(String staffId) {

        ResultInfo resultInfo = signService.queryVisitRecord(staffId);

        return resultInfo;
    }

    /**
     * 获取当前招商人员附近所有需要拜访的对象
     * @param locationAte 驻点玮度
     * @param locatinLte 驻点经度
     * @return
     */
    @GetMapping("/getVisitInfo")
    public ResultInfo getVisitInfo(String staffId, Double locationAte, Double locatinLte) {

        ResultInfo resultInfo = signService.getVisitRecord(staffId, locationAte, locatinLte);

        return resultInfo;
    }

    /**
     * 获取当前本周或本月各组签到情况
     * @return
     */
    @GetMapping("/statisticAll")
    public ResultInfo statisticAll(String startTime, String endTime) {

        ResultInfo resultInfo = signService.getStatisticAll(startTime, endTime);

        return resultInfo;
    }

    /**
     * 获取当前组的情况
     * @param showType 显示类型 0:列表，1:图表
     * @return
     */
    @GetMapping("/statisticByGroup")
    public ResultInfo statisticByGroup(String groupId, String startTime, String endTime, Integer showType) {

        Boolean typeValue = false;
        if (showType == 1) {
            typeValue = true;
        }

        ResultInfo resultInfo = signService.getStatisticByGroup(groupId, startTime, endTime, typeValue);

        return resultInfo;
    }

    /**
     * 获取当前组飞行数据
     * @return
     */
    @GetMapping("/getVisitDataGroup")
    public ResultInfo getVisitDataGroup(String staffId, String groupId, String startTime, String endTime) {

        ResultInfo resultInfo = signService.getFlyDataByGroup(staffId, groupId, startTime, endTime, true);

        return resultInfo;
    }

    /**
     * 获取当前人的飞行数据
     * staffId 当前登录的人 queryStaffId 查询的人
     * @return
     */
    @GetMapping("/getVisitDataStaff")
    public ResultInfo getVisitDataStaff(String staffId, String queryStaffId, String startTime, String endTime) {

        ResultInfo resultInfo = signService.getFlyDataByStaff(staffId, queryStaffId, startTime, endTime);

        return resultInfo;
    }

    /**
     * 获取当前招商人员的情况
     * @return
     */
    @GetMapping("/statisticByStaff")
    public ResultInfo statisticByStaff(String staffId, String nowTime) {

        //获取当前招商人的数据 总签到率
        ResultInfo resultInfo = signService.getStatisticByStaff(staffId, nowTime, nowTime);

        return resultInfo;
    }

    /**
     * 获取当前组有哪些人
     * @return
     */
    @GetMapping("/getStaffByGroup")
    public ResultInfo getStaffByGroup(String staffId, String groupId, Integer type) {

        ResultInfo resultInfo = signService.getStaffByGroup(staffId, groupId, type);

        return resultInfo;
    }

    /**
     * 获取当前招商人员一个月的打卡情况
     * @return
     */
    @GetMapping("/getMonthByStaff")
    public ResultInfo getMonthByStaff(String staffId, String startTime) {

        ResultInfo resultInfo = signService.getSignStationByStaff(staffId, startTime, startTime);

        return resultInfo;
    }

    /**
     * 获取所有组的飞行数据
     * @return
     */
    @GetMapping("/getFlyAllGroup")
    public ResultInfo getFlyAllGroup(String staffId, String startTime, String endTime) {

        //获取当前招商人的数据 总签到率
        ResultInfo resultInfo = signService.getFlyAllGroup(staffId, startTime, endTime);

        return resultInfo;
    }

}
