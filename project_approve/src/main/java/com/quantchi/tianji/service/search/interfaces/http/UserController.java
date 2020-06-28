package com.quantchi.tianji.service.search.interfaces.http;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.enums.ErrCode;
import com.quantchi.tianji.service.search.exception.TianjiErrorCode;
import com.quantchi.tianji.service.search.model.User;
import com.quantchi.tianji.service.search.model.UserDetail;
import com.quantchi.tianji.service.search.model.vo.JobInfo;
import com.quantchi.tianji.service.search.service.CompanyFollowerService;
import com.quantchi.tianji.service.search.service.StatusLogService;
import com.quantchi.tianji.service.search.service.UserService;
import com.quantchi.tianji.service.search.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: DeQing-InvestmentEnterprise
 * @description:
 * @author: mf
 * @create: 2019-09-09 14:49
 **/
@CrossOrigin
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyFollowerService companyFollowerService;

    @Autowired
    private StatusLogService statusLogService;


    @GetMapping("/followCompany")
    public ResultInfo followCompany(String userId, String companyId, String from, String origin) {
        log.info("关注企业,userId:{},companyId:{},from:{}", userId, companyId, from);
        ResultInfo resultInfo = userService.followCompany(userId,companyId, from, origin);
        return resultInfo;
    }

    @GetMapping("/unFollowCompany")
    public ResultInfo unFollowCompany(String userId, String companyId, String from) {
        log.info("取消关注企业,userId:{},companyId:{},from:{}", userId, companyId, from);
        ResultInfo resultInfo = userService.unFollowCompany(userId,companyId);
        return resultInfo;
    }

    @GetMapping(value = "/listFollowCompanys",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public String listFollowCompanys(String userId,Integer page, Integer pageSize) {
        ResultInfo resultInfo = companyFollowerService.listFollowCompanys(userId,page,pageSize);
        return JSONObject.toJSONString(resultInfo, SerializerFeature.WriteMapNullValue);
    }

    @GetMapping(value = "/isCompanysFollowed")
    public String listFollowedCompany(String staffId,String companyIds) {
        ResultInfo resultInfo = userService.isCompanysFollowed(staffId, companyIds);
        return JSONObject.toJSONString(resultInfo, SerializerFeature.WriteMapNullValue);
    }

    @GetMapping(value = "/listLoggedCompany",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public String listLoggedCompany(String staffId,Integer page, Integer pageSize) {
        ResultInfo resultInfo = statusLogService.listMyLoggedCompany(staffId,page,pageSize);
        return JSONObject.toJSONString(resultInfo, SerializerFeature.WriteMapNullValue);
    }

    @GetMapping(value = "/getDetail")
    public ResultInfo getDetail(String staffId) {
        User userInfo = userService.getUserInfo(staffId);
        if(userInfo == null) {
            return ResultUtils.fail(ErrCode.DATA_UPLOADING);
        }
        return ResultUtils.success(userInfo);
    }
    @PostMapping(value = "/insertUserBasicDetail")
    public ResultInfo insertUserBasicDetail(@RequestBody Map<String, Object> condition){
        userService.insertUserBasicDetail(condition);
        return ResultUtils.success("success");
    }
    @PostMapping(value = "/insertUserImg")
    public ResultInfo insertUserImg(MultipartFile file, String userId){
        Map<String, Object> condition = new HashMap<>();
        condition.put("file", file);
        condition.put("userId", userId);
        userService.insertUserImg(condition);
        return ResultUtils.success("success");
    }

    @GetMapping(value = "/getUserDetailInfo")
    public ResultInfo getUserDetail(String userId) {
        UserDetail userDetail = userService.getUserDetail(userId);
        return ResultUtils.success(userDetail);
    }
    @PostMapping(value = "/insertUserDetail")
    public ResultInfo insertUserDetail(@RequestBody Map<String, Object> condition){
        userService.insertUserDetail(condition);
        return ResultUtils.success("success");
    }
    @GetMapping(value = "/deleteUserDetail")
    public ResultInfo deleteUserDetail(int id, int flag){
        userService.deleteUserDetail(id,flag);
        return ResultUtils.success("success");
    }

    @GetMapping(value = "/queryLeaderInfo")
    public ResultInfo queryLeaderInfo(String staffId){

        JobInfo jobInfo = userService.queryLeaderInfo(staffId);

        return ResultUtils.success(jobInfo);
    }

    @GetMapping(value = "/setLeaderInfo")
    public ResultInfo setLeaderInfo(String staffId, String leaderId, String location, Double longitude, Double latitude){

        ResultInfo resultInfo = userService.setLeaderInfo(staffId, leaderId, location, longitude, latitude);

        return ResultUtils.success(resultInfo);
    }

    @GetMapping(value = "/operateManual")
    public ResultInfo operateManual(){

        ResultInfo resultInfo = userService.getOperateManual();

        return ResultUtils.success(resultInfo);
    }

    /**
     * 项目查询钉钉页面（权限接口）
     * @return
     */
    @GetMapping("/searchAuthority")
    public ResultInfo searchAuthority(String staffId) {

        ResultInfo resultInfo = userService.searchAuthority(staffId);

        return resultInfo;
    }

    /**
     * 获取该项目所有相关人员
     * @return
     */
    @GetMapping("/getProjectMember")
    public ResultInfo getProjectMember(String visitId) {

        ResultInfo resultInfo = userService.getProjectMember(visitId);

        return resultInfo;
    }

}
