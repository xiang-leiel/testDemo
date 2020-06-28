package com.quantchi.tianji.service.search.service.impl;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.dao.*;
import com.quantchi.tianji.service.search.enums.ErrCode;
import com.quantchi.tianji.service.search.enums.JobEnum;
import com.quantchi.tianji.service.search.model.*;
import com.quantchi.tianji.service.search.model.vo.JobInfo;
import com.quantchi.tianji.service.search.service.UserService;
import com.quantchi.tianji.service.search.service.sign.impl.UserInfoService;
import com.quantchi.tianji.service.search.utils.PDFUtils;
import com.quantchi.tianji.service.search.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.annotation.Resource;
import java.io.*;
import java.sql.Timestamp;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @program: DeQing-InvestmentEnterprise
 * @description:
 * @author: mf
 * @create: 2019-09-09 09:40
 **/
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Resource
    private DmCsUserMapper dmCsUserMapper;

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private InvUserDetailMapper invUserDetailMapper;

    @Resource
    private SettingValueMapper settingValueMapper;

    @Resource
    private VisitRecordMapper visitRecordMapper;

    @Resource
    private DmCsDeptMapper dmCsDeptMapper;

    @Value("${pic.url}")
    private String picUrl;

    @Override
    public void saveUserLocation(String staffId, Double latitude, Double longitude) {
        userDao.saveUserLocation(staffId, latitude, longitude );
    }

    @Override
    public ResultInfo followCompany(String userId, String companyId, String from, String origin) {
        userDao.followCompany(userId, companyId, from, origin);
        return ResultUtils.success("success");
    }

    @Override
    public ResultInfo unFollowCompany(String userId, String companyId) {
        userDao.unFollowCompany(userId, companyId);
        return ResultUtils.success("success");
    }

    @Override
    public ResultInfo isCompanysFollowed(String staffId, String companyIds) {

        String[] idArray = companyIds.split(",");
        List<String> companyIdList = Arrays.asList(idArray);
        List<String> followedCompanydIds = userDao.listCompanysFollowed(staffId);

        List<String> followedIds = new ArrayList<>();
        if(followedCompanydIds == null || followedCompanydIds.size() <=0 ) {
            return ResultUtils.success(followedIds);
        }

        for (String companyId : companyIdList) {
            if (followedCompanydIds.contains(companyId)) {
                followedIds.add(companyId);
            }
        }
        return ResultUtils.success(followedIds);
    }

    @Override
    public ResultInfo updateTime(String staffId, String time) {
        UserDetail userDetail = new UserDetail();
        userDetail.setUserId(staffId);
        switch (time) {
            case "messageTime":
                userDetail.setMessageTime(new Date());
                userDao.updateTime(userDetail);
                break;
            case "indexNotice":
                userDetail.setIndexNoticeTime(new Date());
                userDao.updateTime(userDetail);
                break;
            case "channelTime":
                userDetail.setChannelTime(new Date());
                userDao.updateTime(userDetail);
                break;
        }
        return ResultUtils.success("success");
    }

    @Override
    public User getUserInfo(String staffId) {
        User userInfo = userDao.getUserInfo(staffId);
        int count = userDao.getAutoPassApply(staffId);
        //查询是否为未同步的数据
        if(userInfo == null) {
            return null;
        }
        userInfo.setPassFlag(count);
        userInfo.setImgUrl(picUrl + userInfo.getImgUrl());
        return userInfo;
    }

    @Override
    public void insertUserBasicDetail(Map<String, Object> condition) {

        String group = (String)condition.get("group");
        if(null != group) {
            SettingValue settingValue = settingValueMapper.queryByGroup(group);
            if(null != settingValue) {
                condition.put("region",settingValue.getRemark());
            }
        }

        userDao.insertUserBasicDetail(condition);
    }
    @Override
    public void insertUserImg(Map<String, Object> condition) {
        Map<String, Object> map = new HashMap<>();
        MultipartFile file = (MultipartFile) condition.get("file");
        Timestamp timestamp= Timestamp.valueOf(LocalDateTime.now());
        String name = file.getOriginalFilename();
        String ext = name.substring(name.lastIndexOf(".")+1);
        String filename = String.valueOf(timestamp.getTime()) ;
        /*File localFile = new File("./pic/"+filename+"."+ext);
        try {
            file.transferTo(localFile);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        filename = filename+"."+ext;
        String filePath = "./pic";
        try {
            fileUpload(file.getBytes(), filePath, filename);
        } catch (Exception e) {
        }
        map.put("imgUrl", "/pic/"+filename);
        map.put("userId", condition.get("userId"));
        userDao.insertUserBasicDetail(map);
    }

    @Override
    public JobInfo queryLeaderInfo(String staffId) {

        //获取当前用户的信息
        UserInfo userInfo = userInfoService.selectUserInfo(staffId);

        JobInfo jobInfo = new JobInfo();

        jobInfo.setLocation(userInfo == null ? null : userInfo.getStationLocation());

        if (null == userInfo) {
            return jobInfo;
        }

        //获取其所在组的所有组员信息,校验是否有组长的存在 如果有则显示组长是谁及驻点地址
        List<String> userList = invUserDetailMapper.queryStaffId(userInfo.getStaffGroup());

        for (String str : userList) {
            UserInfo user = userInfoService.selectUserInfo(str);
            if(user.getStaffJob() != null && user.getStaffJob() == 1) {
                jobInfo.setJob(1);
                jobInfo.setLeaderName(user.getStaffName());
                jobInfo.setLeaderId(user.getStaffId());
                jobInfo.setMobile(user.getMobile());
                return jobInfo;
            }
        }

        return jobInfo;
    }

    @Override
    public ResultInfo setLeaderInfo(String staffId, String leaderId, String location, Double longitude, Double latitude) {

        if (staffId == null) {
            return ResultUtils.fail(ErrCode.NOT_NULL.getCode(),"为传入用户staffId");
        }

        if (StringUtils.isBlank(leaderId)) {
            //进行驻点地址更新
            updateLeaderInfo(staffId, JobEnum.MEMBER.getCode(), location, longitude, latitude);
        }

        //校验当前组是否有组长
        JobInfo jobInfo = queryLeaderInfo(staffId);

        Boolean isLeader = false;
        UserInfo userInfo = userInfoService.selectUserInfo(staffId);
        if(userInfo != null) {
            if(userInfo.getStaffJob() == 1) {
                isLeader = true;
            }
            jobInfo.setLocation(userInfo.getStationLocation());
        }

        Boolean existLeader = jobInfo.getJob() != null && JobEnum.LEADER .getCode() == jobInfo.getJob();

        if(existLeader && isLeader){
            //存在组长,当前用户为组长 可修改组长信息
            updateLeaderInfo(leaderId, JobEnum.LEADER.getCode(), null, null, null);

            //修改原组长为组员
            updateLeaderInfo(staffId, JobEnum.MEMBER.getCode(), null, null, null);
        }else if(!existLeader) {
            //不存在组长 可设置组长
            updateLeaderInfo(leaderId, JobEnum.LEADER.getCode(), null, null, null);
        }

        return ResultUtils.success("true");
    }

    @Override
    public ResultInfo getOperateManual() {

        List<String> result = new ArrayList<>();

        if(true) {

            File fileNew = new File("/pic/operateManual");

            File[] files = fileNew.listFiles();
            log.info("文件的大小为{}",files == null ? null : files.length);

            for(int i = 0; i < 5; i++) {

                String imgUrlResult = picUrl + "/pic/operateManual/" + i + ".jpg";
                result.add(imgUrlResult);

            }
            return ResultUtils.success(result);

        }

        String filePath = "/pic/operateManual.pdf";

        File file = new File("/pic/operateManual/0.jpg");

        File fileNew = new File("/pic/operateManual");

        //判断文件是否存在
        if(file.exists()) {

            File[] files = fileNew.listFiles();

            for(int i = 0; i < files.length ; i++) {

                String imgUrlResult = picUrl + "/pic/operateManual/" + i + ".jpg";
                result.add(imgUrlResult);

            }
            return ResultUtils.success(result);

        }

        //Pdf转成png
        List<String> imageList = PDFUtils.pdfToImagePath(filePath);

        for (String imgUrl : imageList) {
            String imgUrlResult = picUrl + imgUrl;
            result.add(imgUrlResult);
        }

        return ResultUtils.success(result);
    }

    @Override
    public ResultInfo searchAuthority(String staffId) {
        //根据手机号校验权限
        UserInfo userInfo1 = userInfoService.selectUserInfo(staffId);

        Boolean viewFlag = false;
        if(userInfo1 == null || userInfo1.getMobile() == null) {
            viewFlag = false;
        }else if(userInfo1.getMobile() != null) {
            List<Integer> jobIds = dmCsUserMapper.getJobByMobile(userInfo1.getMobile());
            if(jobIds.contains(50010001)) {
                viewFlag = true;
            }
        }
        if(!viewFlag) {
            return ResultUtils.fail(1001, "您的权限不足，无法查看");
        }

        return ResultUtils.success("success");
    }

    @Override
    public ResultInfo getProjectMember(String visitId) {

        List<StaffVO> staffVOS = new ArrayList<>();

        List<Integer> list = new ArrayList<>();

        Integer userId =  visitRecordMapper.selectByVisitId(visitId);

        if(userId == null) {
            log.error("用户项目行程不存在{}", visitId);
            return ResultUtils.success(staffVOS);
        }

/*        List<Integer> partnerList = visitRecordMapper.selectByMasterId(visitId);

        list.add(userId);
        list.addAll(partnerList);

        for(Integer integer : list) {
            StaffVO staffVO = new StaffVO();
            DmCsUser dmCsUser = dmCsUserMapper.selectByPrimaryKey(integer);
            staffVO.setName(dmCsUser.getXm());
            staffVO.setUserId(integer);
            staffVO.setGroupId(dmCsUser.getDeptDm());
            DmCsDept dmCsDept = dmCsDeptMapper.selectByPrimaryKey(dmCsUser.getDeptDm());
            staffVO.setGroupName(dmCsDept.getBmmc());
            staffVOS.add(staffVO);
        }*/

        StaffVO staffVO = new StaffVO();
        DmCsUser dmCsUser = dmCsUserMapper.selectByPrimaryKey(userId);
        staffVO.setName(dmCsUser.getXm());
        staffVO.setUserId(userId);
        staffVO.setGroupId(dmCsUser.getDeptDm());
        DmCsDept dmCsDept = dmCsDeptMapper.selectByPrimaryKey(dmCsUser.getDeptDm());
        staffVO.setGroupName(dmCsDept.getBmmc());
        staffVOS.add(staffVO);

        List<DmCsUser> dmCsUserList = dmCsUserMapper.selectByDeptDm(dmCsUser.getDeptDm());
        for(DmCsUser dmCs : dmCsUserList) {
            StaffVO staff = new StaffVO();
            if(dmCs.getUserDm().equals(userId)) {
                continue;
            }
            staff.setName(dmCs.getXm());
            staff.setUserId(dmCs.getUserDm());
            staff.setGroupId(dmCs.getDeptDm());
            staffVOS.add(staff);
        }

        return ResultUtils.success(staffVOS);
    }

    private void updateLeaderInfo(String staffId, Integer leader, String location, Double longitude, Double latitude) {
        //没有组长，则可以设置组长，并输入驻点地址
        userDao.updateForJob(staffId, location, leader, longitude, latitude, new Date());

        //同时另起线程修改其他组员驻点地址数据
        //syncToAllStaff(staffId, location, longitude, latitude);
    }

    public void syncToAllStaff(final String staffId, final String location, final Double longitude, final Double latitude) {

        log.info("异步同步各组员信息开始{}", staffId);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    UserInfo userInfo = userInfoService.selectUserInfo(staffId);
                    //获取其所在组的所有组员信息,校验是否有组长的存在 如果有则显示组长是谁及驻点地址
                    List<String> userList = invUserDetailMapper.queryStaffId(userInfo.getStaffGroup());

                    for (String str : userList) {
                        if(!staffId.equals(str)) {

                            //更新组员信息
                            userDao.updateForJob(str, location, JobEnum.MEMBER.getCode(), longitude, latitude, new Date());

                        }
                    }

                }catch (Exception e) {
                    log.error("更新数据失败, e={}", e);
                }
                log.info("异步同步各组员信息结束");
            }
        }).start();
    }



    @Override
    public UserDetail getUserDetail(String userId) {
        UserDetail userDetail = userDao.getUserBasicDetail(userId);
        if (userDetail != null) {
            if (userDetail.getImgUrl() != null) {
                userDetail.setImgUrl(picUrl+userDetail.getImgUrl());
            }
        }
        JobInfo jobInfo = queryLeaderInfo(userId);

        userDetail.setJobInfo(jobInfo);

        return userDetail;
    }

    @Override
    public void insertUserDetail(Map<String, Object> condition) {
        int flag = (int) condition.get("flag");
        switch (flag){
            case 1:
                if (condition.get("id") != null) {
                    userDao.updateUserEducationInfo(condition);
                }else {
                    userDao.insertUserEducationInfo(condition);
                }
                break;
            case 2:
                if (condition.get("id") != null) {
                    userDao.updateUserWorkInfo(condition);
                }else {
                    userDao.insertUserWorkInfo(condition);
                }
                break;
            case 3:
                if (condition.get("id") != null) {
                    userDao.updateUserCityInfo(condition);
                }else {
                    userDao.insertUserCityInfo(condition);
                }
                break;
            case 4:
                if (condition.get("id") != null) {
                    userDao.updateUserBusinessInfo(condition);
                }else {
                    userDao.insertUserBusinessInfo(condition);
                }
                break;
        }
    }

    @Override
    public void deleteUserDetail(int id, int flag) {
        switch (flag){
            case 1:
                userDao.deleteUserEducationById(id);
                break;
            case 2:
                userDao.deleteUserWorkById(id);
                break;
            case 3:
                userDao.deleteUserCityById(id);
                break;
            case 4:
                userDao.deleteUserBusinessInfoById(id);
                break;
        }
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
