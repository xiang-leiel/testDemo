package com.quantchi.tianji.service.search.service.sign.impl;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.dao.*;
import com.quantchi.tianji.service.search.enums.ErrCode;
import com.quantchi.tianji.service.search.enums.ProjectStatusEnum;
import com.quantchi.tianji.service.search.model.*;
import com.quantchi.tianji.service.search.model.vo.ProjectInfo;
import com.quantchi.tianji.service.search.model.vo.sign.*;
import com.quantchi.tianji.service.search.service.sign.SignService;
import com.quantchi.tianji.service.search.utils.DateUtils;
import com.quantchi.tianji.service.search.utils.DistanceUtils;
import com.quantchi.tianji.service.search.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Description 签到服务实现类
 * @author leiel
 * @Date 2019/12/9 9:07 PM
 */
@Service
@Slf4j
public class SignServiceImpl implements SignService {

    @Resource
    private InvUserDetailMapper invUserDetailMapper;

    @Resource
    private SignRecordMapper signRecordMapper;

    @Resource
    private VisitRecordMapper visitRecordMapper;

    @Resource
    private SignDealService signDealService;

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private ProjectRecordMapper projectRecordMapper;

    @Resource
    private UserDao userDao;

    @Value("${pic.url}")
    private String picUrl;

    public final static String MEET_URL = "http://47.111.121.200/pic/dingding/meeting.png";
    public final static String COMPANY_URL = "http://47.111.121.200/pic/dingding/company.png";

    @Override
    public ResultInfo getUserInfo(String staffId) {

        UserInfo userInfo = userInfoService.selectUserInfo(staffId);

        //封装用户参数返回
        if(userInfo == null) {
            //未查询到用户相关数据

            //查询是否为未同步的数据
            String mobile = userDao.getUserByStaffId(staffId);

            if(StringUtils.isBlank(mobile)) {
                return ResultUtils.fail(ErrCode.DATA_UPLOADING);
            }

            return ResultUtils.fail(ErrCode.DATA_QUERY_FAIL);
        }

        UserVO userVO = new UserVO();
        userVO.setStaffId(staffId);
        userVO.setNowTime(new Date());
        userVO.setStaffImg(StringUtils.isBlank(userInfo.getStaffImgUrl())? null : picUrl + userInfo.getStaffImgUrl());

        Date start = DateUtils.getDayAm(new Date());
        Date end = DateUtils.getDayPm(new Date());

        log.info("开始用户数据处理staffId={}", staffId);
        signDealService.dealSignRecordByStaffId(userVO, userInfo, staffId, start, end);

        //是否提醒添加行程
        signDealService.addDistanceDeal(userVO);
        log.info("用户数据处理完毕userVO={}", userVO);

        return ResultUtils.success(userVO);
    }

    @Override
    public ResultInfo saveUserSignRecord(String staffId, Long visitId, String visitLocation, Double visitAte, Double visitLte) {

        if(null == visitAte || null == visitLte) {
            return ResultUtils.fail(ErrCode.DATA_GET_FAIL);
        }

        SignRecord signRecord = signRecordMapper.fetchOne(staffId, DateUtils.getDayAm(new Date()), DateUtils.getDayPm(new Date()));

        if (signRecord != null && null == visitId) {
            return ResultUtils.fail(ErrCode.NOT_SIGN);
        }

        UserInfo userInfo = userInfoService.selectUserInfo(staffId);

        if(userInfo != null && userInfo.getStaffGroup() == null) {
            return ResultUtils.fail(ErrCode.NOT_EXIST.getCode(),"请设置用户所属组别");
        }

        Integer result = null;
        //Integer result = signDealService.dealSignAfter(staffId, visitId, visitLocation, visitAte, visitLte);

        if(null == result) {
            return ResultUtils.fail(ErrCode.SIGNED);
        }

        if (result > 0) {
            return getUserInfo(staffId);
        }

        //插入失败
        return ResultUtils.fail(ErrCode.DATA_INSERT_FAIL);
    }

    @Override
    public ResultInfo addVisitRecord(VisitRecordParam visitRecordParam) {

        //参数校验
        if(StringUtils.isBlank(visitRecordParam.getVisitTime())) {
            return ResultUtils.fail(ErrCode.NOT_NULL.getCode(), "拜访名称");
        }
        if(StringUtils.isBlank(visitRecordParam.getVisitLocation())) {
            return ResultUtils.fail(ErrCode.NOT_NULL.getCode(), "拜访地址");
        }
        if(null == visitRecordParam.getVisitType()) {
            return ResultUtils.fail(ErrCode.NOT_NULL.getCode(), "拜访类型");
        }

        //获取当前用户所属组
        UserInfo userInfo = userInfoService.selectUserInfo(visitRecordParam.getStaffId());

        VisitRecord visitRecord = new VisitRecord();
        visitRecord.setStaffId(visitRecordParam.getStaffId());
        visitRecord.setVisitType(visitRecordParam.getVisitType());
        visitRecord.setVisitName(visitRecordParam.getVisitName());
        visitRecord.setVisitLocation(visitRecordParam.getVisitLocation());
        visitRecord.setVisitLatitude(visitRecordParam.getVisitAte());
        visitRecord.setVisitLongitude(visitRecordParam.getVisitLte());
        visitRecord.setImgUrl(visitRecordParam.getVisitType() == 1 ? MEET_URL : COMPANY_URL);
        Date visitTime = DateUtils.getDateYYYYMMddHHMMSS(dealTimeData(visitRecordParam.getVisitTime()));
        visitRecord.setVisitTime(visitTime);
        visitRecord.setStatus(ProjectStatusEnum.WAIT_VISIT.getCode());
        visitRecord.setTogether(0);
        int insertResult = 0;
        try {
            insertResult = visitRecordMapper.insertSelective(visitRecord);
        } catch (Exception e) {
            log.error("插入用户未拜访记录失败{}",visitRecordParam.getStaffId());
            return ResultUtils.fail(ErrCode.DATA_INSERT_FAIL);
        }

        StringBuffer partnerTotal = new StringBuffer();
        //校验当前邀请人是否和当前招商人同组
        if(StringUtils.isNotEmpty(visitRecordParam.getVisitPartner())) {

            String[] partners = visitRecordParam.getVisitPartner().split(",");

            for (String str : partners) {

                UserInfo partnerInfo = userInfoService.selectUserInfo(str);
                if (!partnerInfo.getStaffGroup().equals(userInfo.getStaffGroup())) {
                    return ResultUtils.fail(ErrCode.PARTNER_ERROR);
                }
                partnerTotal.append(partnerInfo.getStaffName()+",");

                //插入同行人拜访数据
                VisitRecord visitRecord1 = new VisitRecord();
                visitRecord1.setStaffId(str);
                visitRecord1.setVisitType(visitRecordParam.getVisitType());
                visitRecord1.setVisitName(visitRecordParam.getVisitName());
                visitRecord1.setVisitLocation(visitRecordParam.getVisitLocation());
                visitRecord1.setVisitLatitude(visitRecordParam.getVisitAte());
                visitRecord1.setVisitLongitude(visitRecordParam.getVisitLte());
                visitRecord1.setImgUrl(visitRecordParam.getVisitType() == 1 ? MEET_URL : COMPANY_URL);
                visitRecord1.setVisitTime(visitTime);
                visitRecord1.setStatus(ProjectStatusEnum.INVALID.getCode());
                visitRecord1.setTogether(1);
                visitRecord1.setMasterId(visitRecord.getId());
                visitRecordMapper.insertSelective(visitRecord1);
            }
            partnerTotal.deleteCharAt(partnerTotal.length() - 1);

        }

        //更新visitRecord
        VisitRecord visitRecord1 = new VisitRecord();
        visitRecord1.setId(visitRecord.getId());
        visitRecord1.setVisitPartner(partnerTotal == null ? null : partnerTotal.toString());
        visitRecordMapper.updateByPrimaryKeySelective(visitRecord1);

        return ResultUtils.success(insertResult);
    }

    @Override
    public ResultInfo getVisitRecord(String staffId, Double locationAte, Double locatinLte) {

        //获取当前用户所有需拜访对象
        List<VisitRecord> visitRecordList = fetchVisitingRevord(staffId, false);

        //对所有数据进行排序
        dealVisitData(visitRecordList, locationAte, locatinLte);

        //数据封装
        List<VisitParam> visitParams = new ArrayList<>();
        visitParams = buildVisitData(visitRecordList, locationAte, locatinLte);

        return ResultUtils.success(visitParams);
    }

    /**
     * 对用户所有数据处理
     * @param staffId
     * @param visitedFlag true:为获取已签到的数据 false:为获取未签到的数据
     * @return
     */
    private List<VisitRecord> fetchVisitingRevord(String staffId, Boolean visitedFlag) {

        //获取当前用户所有拜访数据
        List<VisitRecord> visitRecordList = null;

        //待拜访数据
        List<VisitRecord> visitingRecord = new ArrayList<>();

        //已拜访数据
        List<VisitRecord> visitedRecord = new ArrayList<>();

        for (VisitRecord visitRecord : visitRecordList) {

            SignRecord signRecord = signRecordMapper.selectByVisitId(visitRecord.getId(), staffId, null, null);
            if(signRecord == null) {
                visitingRecord.add(visitRecord);
            } else {
                visitedRecord.add(visitRecord);
            }
        }

        if(visitedFlag) {
            return visitedRecord;
        }

        return visitingRecord;

    }


    @Override
    public ResultInfo getStatisticAll(String startTime, String endTime) {

        startTime = dealTimeData(startTime);
        endTime = dealTimeData(endTime);

        //获取所有组的签到，行程数据
        List<GroupSignVO> groupSignVOS = getLastResult(startTime, endTime);

        //排序
        signDealService.signRateSort(groupSignVOS);

        return ResultUtils.success(groupSignVOS);
    }

    @Override
    public ResultInfo getStatisticByGroup(String groupId, String startTime, String endTime, Boolean showType) {

        startTime = dealTimeData(startTime);
        endTime = dealTimeData(endTime);

        //获取当前组的数据 签到率 排名、人均里程 排名
        Map<String, GroupSignVO> groupMap = calStatitonGroup(startTime, endTime);

        GroupSignVO groupSignVO = groupMap.get(groupId);

        int totalDay = DateUtils.getIntervalDays(startTime, endTime);

        //获取该组驻点信息
        InvUserDetail user = invUserDetailMapper.queryStaffIdOne(groupId);

        if(user == null) {
            return ResultUtils.fail(ErrCode.USER_DATA_NOEXIST);
        }
        groupSignVO.setGroupLocRegion(user.getRegion());

        //驻点地址修改--取组长，若无组长显示暂未设置
        String location = dealLocation(user.getGroup());

        groupSignVO.setGroupLocation(location);
        log.info("开始处理用户数据groupId={},user={}", groupId, user);

        //如果为列表
        if (showType) {
            signDealService.dealColumnData(groupSignVO, groupId, totalDay, startTime, endTime);
        } else {
            //如果为图表  签到率、行程、拜访次数
            signDealService.dealChartData(groupSignVO, groupId, totalDay, startTime, endTime);

            //计算环比数据
            if(totalDay > 7) {
                //获取上个月的数据
                startTime = DateUtils.getEarlyMonth(startTime);
                endTime = DateUtils.getlateMonth(endTime);
            } else {
                endTime = startTime;
                startTime = DateUtils.DateChangeByDays(startTime, -7, true);
            }

            GroupSignVO cycGroupSign = new GroupSignVO();
            signDealService.dealChartData(cycGroupSign, groupId, totalDay, startTime, endTime);

            groupSignVO.setGroupSignCyc(calCyc(new BigDecimal(groupSignVO.getGroupSignTotal()), new BigDecimal(cycGroupSign.getGroupSignTotal())));
            groupSignVO.setGroupDistanceCyc(calCyc(groupSignVO.getGroupDistanceTotal(),cycGroupSign.getGroupDistanceTotal()));
            groupSignVO.setGroupVisitCyc(calCyc(groupSignVO.getGroupVisitCount(), cycGroupSign.getGroupVisitCount()));

        }

        //获取所有组的数据
        List<String> listGroup = invUserDetailMapper.queryGroup();
        signDealService.dealGroup(listGroup);
        groupSignVO.setGroupTotal(listGroup.size());

        return ResultUtils.success(groupSignVO);
    }

    private String dealLocation(String group) {
        //获取其所在组的所有组员信息,校验是否有组长的存在 如果有则显示组长是谁及驻点地址
        List<String> userList = invUserDetailMapper.queryStaffId(group);

        for (String str : userList) {
            UserInfo user = userInfoService.selectUserInfo(str);
            if(user.getStaffJob() != null && user.getStaffJob() == 1) {
                return user.getStationLocation();
            }
        }

        String noLeader = "暂未设置";

        return noLeader;
    }

    @Override
    public ResultInfo getFlyDataByGroup(String staffId, String groupId, String startTime, String endTime, Boolean flag) {

        startTime = dealTimeData(startTime);
        endTime = dealTimeData(endTime);

        //权限控制 当前人是否属于groupId，不属于则无法
        UserInfo userInfo = userInfoService.selectUserInfo(staffId);
        if (flag && !"招商领导".equals(userInfo.getStaffRole()) && !userInfo.getStaffGroup().equals(groupId)) {

            return ResultUtils.fail(ErrCode.NOT_AUTHORITY);

        }

        GroupSignVO groupSignVO = new GroupSignVO();
        groupSignVO.setGroupLocRegion(userInfo.getRegion());

        int totalDay = DateUtils.getIntervalDays(startTime, endTime);

        signDealService.dealChartData(groupSignVO, groupId, totalDay, startTime, endTime);

        //获取当前组每个用户的拜访记录
        signDealService.dealVisitData(groupSignVO, groupId, totalDay, startTime, endTime);

        //获取所有组的数据
        List<String> listGroup = invUserDetailMapper.queryGroup();
        signDealService.dealGroup(listGroup);
        groupSignVO.setGroupTotal(listGroup.size());

        return ResultUtils.success(groupSignVO);
    }

    @Override
    public ResultInfo getFlyDataByStaff(String staffId, String queryStaffId, String startTime, String endTime) {

        startTime = dealTimeData(startTime);
        endTime = dealTimeData(endTime);

        //权限控制 当前人和查询的人是否同属一个组，不属于则无权限
        UserInfo userInfo = userInfoService.selectUserInfo(staffId);
        UserInfo userInfoQuery = userInfoService.selectUserInfo(queryStaffId);
        if (!userInfo.getStaffRole().equals("招商领导") && !userInfo.getStaffGroup().equals(userInfoQuery.getStaffGroup())) {

            return ResultUtils.fail(ErrCode.NOT_AUTHORITY);

        }

        StaffSignVO staffSignVO = new StaffSignVO();

        int totalDay = DateUtils.getIntervalDays(startTime, endTime);

        UserVO userVO = new UserVO();

        String startTimeNew = startTime;
        List<RouteVO> routeVOList = new ArrayList<>();
        for (int i = 0; i < totalDay; i++) {

            //时间格式转换
            Date start = DateUtils.getDayAm(DateUtils.getDateYYYYMMddHHMMSS(startTimeNew));
            Date end = DateUtils.getDayPm(DateUtils.getDateYYYYMMddHHMMSS(startTimeNew));

            signDealService.dealVisitDataDay(userVO, routeVOList, queryStaffId, start, end);

            //日期加一天
            startTimeNew = DateUtils.DateDayChangeNew(startTimeNew);

        }
        staffSignVO.setUserVO(userVO);

        signDealService.dealChartDataForStaff(staffSignVO, queryStaffId, totalDay, startTime, endTime);

        return ResultUtils.success(staffSignVO);
    }

    @Override
    public ResultInfo getStatisticByStaff(String staffId, String startTime, String endTime) {

        int totalDay = Integer.parseInt(DateUtils.dateFormatDay(new Date()));

        Date newValue = DateUtils.getDateYYYYMMddHHMMSS(dealTimeData(startTime));

        //不是同一月，则取当前月的天数
        if(!DateUtils.changeFormatDateYYYYMM(new Date()).equals(DateUtils.changeFormatDateYYYYMM(newValue))) {
            totalDay = DateUtils.getDaysOfMonth(newValue);
        }

        UserInfo userInfo = userInfoService.selectUserInfo(staffId);

        //封装用户参数返回
        if(userInfo == null) {
            //未查询到用户相关数据
            return ResultUtils.fail(ErrCode.DATA_QUERY_FAIL);
        }

        UserVO userVO = new UserVO();
        userVO.setStaffId(staffId);
        userVO.setNowTime(new Date());

        startTime = dealTimeData(startTime);
        endTime = dealTimeData(endTime);

        Date start = DateUtils.getDayAm(DateUtils.getDateYYYYMMddHHMMSS(startTime));
        Date end = DateUtils.getDayPm(DateUtils.getDateYYYYMMddHHMMSS(endTime));

        signDealService.dealVisitByStaffId(userVO, userInfo, staffId, totalDay, start, end);

        return ResultUtils.success(userVO);
    }

    @Override
    public ResultInfo getStaffByGroup(String staffId, String groupId, Integer type) {

        //获取当前组下的所有成员
        List<String> userList = invUserDetailMapper.queryStaffId(groupId);
        List<User> userAll = new ArrayList<>();

        for (String str : userList) {
            if (str.equals(staffId) && null != type) {
                continue;
            }
            UserInfo userInfo =userInfoService.selectUserInfo(str);
            User user = new User();
            user.setImgUrl(StringUtils.isBlank(userInfo.getStaffImgUrl())? null : picUrl + userInfo.getStaffImgUrl());
            user.setName(userInfo.getStaffName());
            user.setId(userInfo.getStaffId());
            userAll.add(user);
        }

        return ResultUtils.success(userAll);
    }

    @Override
    public ResultInfo getSignStationByStaff(String staffId, String startTime, String endTime) {

        startTime = DateUtils.getFirstDayDateOfMonth(dealTimeData(startTime));
        endTime = DateUtils.getLastDayOfMonth(dealTimeData(endTime));

        List<UserVO> userVOList = new ArrayList<>();

        int totalDay = DateUtils.getIntervalDays(startTime, endTime);

        signDealService.dealSignDataStaff(staffId, userVOList, totalDay, startTime, endTime);

        return ResultUtils.success(userVOList);
    }

    @Override
    public ResultInfo getFlyAllGroup(String staffId, String startTime, String endTime) {

        //权限控制 当前人是否属于groupId，不属于则无法
        UserInfo userInfo = userInfoService.selectUserInfo(staffId);
        if (!"招商领导".equals(userInfo.getStaffRole())) {
            return ResultUtils.fail(ErrCode.NOT_AUTHORITY);
        }

        //获取所有组的数据
        List<String> listGroup = invUserDetailMapper.queryGroup();

        GroupSignVO groupSignVO = new GroupSignVO();

        Integer groupSignTotal = 0;

        BigDecimal groupDistanceTotal = BigDecimal.ZERO;

        Integer groupVisitTotal = 0;

        Integer groupReceiveTotal = 0;

        List<UserVO> userVOList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(listGroup)) {

            for (String groupId : listGroup) {

                if (StringUtils.isBlank(groupId)) {
                    continue;
                }

                ResultInfo resultInfo = getFlyDataByGroup(staffId, groupId, startTime, endTime, false);
                GroupSignVO groupSign = (GroupSignVO) resultInfo.getBody();
                groupSignTotal += groupSign.getGroupSignTotal();
                groupDistanceTotal = groupDistanceTotal.add(groupSign.getGroupDistanceTotal());
                groupVisitTotal += groupSign.getGroupVisitTotal();
                if (null == groupSign.getGroupReceiveTotal()) {
                    groupSign.setGroupReceiveTotal(0);
                }
                groupReceiveTotal += groupSign.getGroupReceiveTotal();
                userVOList.addAll(groupSign.getUserVOList());
            }
        }
        groupSignVO.setGroupSignTotal(groupSignTotal);
        groupSignVO.setGroupDistanceTotal(groupDistanceTotal);
        groupSignVO.setGroupVisitTotal(groupVisitTotal);
        groupSignVO.setGroupReceiveTotal(groupReceiveTotal);
        groupSignVO.setUserVOList(userVOList);

        return ResultUtils.success(groupSignVO);
    }

    @Override
    public ResultInfo queryVisitRecord(String staffId) {

        List<Integer> resultList = new ArrayList<>();

        //未拜访
        List<VisitRecord> visitingList = fetchVisitingRevord(staffId, false);

        //已拜访
        List<VisitRecord> visitedList = fetchVisitingRevord(staffId, true);

        Integer result = visitedList.size();

        if(null == result) {
            result = 0;
        }

        if(CollectionUtils.isEmpty(visitingList)) {
            resultList.add(0);
            resultList.add(0);
        } else {
            resultList.add(result);
            resultList.add(visitingList.size());
        }


        return ResultUtils.success(resultList);
    }

    @Override
    public ResultInfo queryVisitData(String visitId) {

        if(StringUtils.isBlank(visitId)) {
           return ResultUtils.fail(ErrCode.NOT_NULL.getCode(), "visitId");
        }
        VisitRecordParam visitRecordParam = new VisitRecordParam();
        VisitRecord visitRecord = visitRecordMapper.selectByPrimaryKey(visitId);

        BeanUtils.copyProperties(visitRecord, visitRecordParam);
        visitRecordParam.setVisitTime(DateUtils.changeFormatDate(visitRecord.getVisitTime()));

        //获取当前人id
        UserInfo userInfo = userInfoService.selectUserInfo(visitRecordParam.getStaffId());
        visitRecordParam.setStaffName(userInfo.getStaffName());
        return ResultUtils.success(visitRecordParam);
    }

    private BigDecimal calCyc(BigDecimal nowData, BigDecimal lastData) {

        if (lastData == null || lastData.compareTo(BigDecimal.ZERO) < 1) {
            return null;
        }

        if (nowData == null) {
            return null;
        }

        BigDecimal cycData = nowData.subtract(lastData).divide(lastData, 4, BigDecimal.ROUND_DOWN);

        return cycData.multiply(new BigDecimal("100").setScale(1,BigDecimal.ROUND_DOWN));

    }

    /**
     * 计算所有组的签到率，签到排名，行程，行程排名
     * @return
     */
    private Map<String, GroupSignVO> calStatitonGroup (String startTime, String endTime) {

        Map<String, GroupSignVO> signMap = new HashMap<>();

        //获取所有组的签到，行程数据
        List<GroupSignVO> groupSignVOS = getLastResult(startTime, endTime);

        int valueSignCount = 0;

        int valueDistanceCount = 0;

        for (GroupSignVO groupSignVO : groupSignVOS) {
            if(groupSignVO.getGroupSignRate().compareTo(BigDecimal.ZERO) == 0) {
                valueSignCount++;
            }
            if(groupSignVO.getGroupSignDistance().compareTo(BigDecimal.ZERO) == 0) {
                valueDistanceCount++;
            }
        }

        //签到率排序
        signDealService.signRateSort(groupSignVOS);

        //放入Map中便于取数据
        int i = 1;
        for(GroupSignVO groupSignVO : groupSignVOS) {
            if(valueSignCount == groupSignVOS.size()) {
                groupSignVO.setGroupSignRank(0);
            }else {
                groupSignVO.setGroupSignRank(i);
            }
            signMap.put(groupSignVO.getGroupName(), groupSignVO);
            i++;
        }

        //行程排名排序
        signDealService.signDistanceSort(groupSignVOS);

        int j = 1;
        for(GroupSignVO groupSignVO : groupSignVOS) {
            if(valueDistanceCount == groupSignVOS.size()) {
                groupSignVO.setGroupSignDistanceRank(0);
            }else {
                groupSignVO.setGroupSignDistanceRank(j);
            }
            signMap.put(groupSignVO.getGroupName(), groupSignVO);
            j++;
        }

        return signMap;

    }

    private List<GroupSignVO> getLastResult(String startTime, String endTime) {

        //总的天数
        int totalDay = DateUtils.getIntervalDays(startTime, endTime);

        if(totalDay > 7) {
            Date newValue = DateUtils.getDateYYYYMMddHHMMSS(dealTimeData(startTime));

            if(!DateUtils.changeFormatDateYYYYMM(new Date()).equals(DateUtils.changeFormatDateYYYYMM(newValue))) {
                totalDay = DateUtils.getDaysOfMonth(newValue);
                endTime = DateUtils.getLastDayOfMonth(dealTimeData(endTime));
            } else {
                totalDay = Integer.parseInt(DateUtils.dateFormatDay(new Date()));
                endTime = DateUtils.changeFormatDate(new Date());
            }

        }
        log.info("startTime={}, endTime={}", startTime, endTime);

        //获取所有组的数据
        List<String> listGroup = invUserDetailMapper.queryGroup();

        List<GroupSignVO> groupSignVOS = new ArrayList<>();
        //计算各组的签到情况
        for (String str : listGroup) {

            if(StringUtils.isBlank(str)) {
                continue;
            }

            GroupSignVO groupSignVO = new GroupSignVO();

            //获取当前组下的所有成员
            List<String> userList = invUserDetailMapper.queryStaffId(str);

            if (CollectionUtils.isNotEmpty(userList)) {

                UserInfo userInfo = userInfoService.selectUserInfo(userList.get(0));
                groupSignVO.setGroupLocRegion(userInfo.getRegion());
            }

            //总的签到次数
            int totalSign = userList.size() * totalDay;

            //当前组所有的签到次数
            int signtime = 0;
            BigDecimal signDistance = BigDecimal.ZERO;

            //获取每位成员此时间段的签到情况并记录该组员哪天未签到
            for (String staffId : userList) {
                //时间格式转换
                Date start = DateUtils.getDayAm(DateUtils.getDateYYYYMMddHHMMSS(startTime));
                Date end = DateUtils.getDayPm(DateUtils.getDateYYYYMMddHHMMSS(dealTimeData(endTime)));
                List<String> list = signRecordMapper.selectByStaffForSign(staffId, start, end);
                //list的大小就是该招商员签到了几天
                signtime += list.size();

                //计算每位成员当前时间段所有行程数
                Double distance = signRecordMapper.getDistanceByStaff(staffId, start, end);

                if (distance == null) {
                    distance = 0.00;
                }

                //总行程
                signDistance = signDistance.add(BigDecimal.valueOf(distance));

            }

            //当前组的签到率
            BigDecimal signRate = BigDecimal.ZERO;
            if(totalSign != 0) {
                signRate = new BigDecimal(signtime).divide(new BigDecimal(totalSign), 3, BigDecimal.ROUND_DOWN);

            }

            //当前组的平均行程
            BigDecimal rvgDistance = BigDecimal.ZERO;
            if (CollectionUtils.isNotEmpty(userList)) {
                rvgDistance = signDistance.divide(new BigDecimal(userList.size()),1,BigDecimal.ROUND_DOWN);
            }

            //参数封装
            groupSignVO.setGroupSignRate(signRate);
            groupSignVO.setGroupSignDistance(rvgDistance);
            groupSignVO.setGroupName(str);
            groupSignVOS.add(groupSignVO);
        }

        return groupSignVOS;

    }

    /**
     * 封装拜访数据
     * @param
     * @param visitRecordList
     * @param locationAte
     * @param locatinLte
     */
    private List<VisitParam> buildVisitData (List<VisitRecord> visitRecordList, Double locationAte, Double locatinLte) {

        List<VisitParam> visitParams = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(visitRecordList)) {

            for(VisitRecord visitRecord : visitRecordList) {
                VisitParam visitParam = new VisitParam();

                Double distance = DistanceUtils.getDistance(visitRecord.getVisitLongitude(),visitRecord.getVisitLatitude(), locatinLte, locationAte);
                visitParam.setVisitLocation(visitRecord.getVisitLocation());
                visitParam.setVisitName(visitRecord.getVisitName());
                visitParam.setVisitAte(visitRecord.getVisitLatitude().toString());
                visitParam.setVisitLte(visitRecord.getVisitLongitude().toString());
                visitParam.setOverFlag(0);
                visitParam.setProjectId(visitRecord.getId());
                if (distance > 5) {
                    visitParam.setOverFlag(1);
                }
                if (distance > 1) {
                    visitParam.setDistance(String.format("%.1f", distance)+ "km");
                } else {
                    visitParam.setDistance(String.valueOf(Math.round(distance*1000))+ "m");
                }
                visitParams.add(visitParam);
            }

            //overFlag值为0的个数为0返回空，为1返回一个数据
            if(CollectionUtils.isEmpty(visitParams)) {
                return visitParams;
            }
            int overFlagCount = 0;
            for (VisitParam visitParam : visitParams) {
                if(visitParam.getOverFlag() == 0) {
                    overFlagCount++;
                }
            }
            if(overFlagCount == 0) {
                return new ArrayList<>();
            } else if (overFlagCount == 1) {
                List<VisitParam> visitParams1 = new ArrayList<>();
                visitParams1.add(visitParams.get(0));
                return visitParams1;
            }

        }
        return visitParams;

    }

    /**
     * 处理拜访行程数据
     * @param visitRecordList
     * @param locationAte
     * @param locatinLte
     */
    private void dealVisitData(List<VisitRecord> visitRecordList, Double locationAte, Double locatinLte) {

        if (CollectionUtils.isNotEmpty(visitRecordList)) {

            Collections.sort(visitRecordList, new Comparator<VisitRecord>() {
                        @Override
                        public int compare(VisitRecord o1, VisitRecord o2) {
                            double double1 = DistanceUtils.getDistance(o1.getVisitLongitude(),o1.getVisitLatitude(), locatinLte, locationAte);
                            double double2 = DistanceUtils.getDistance(o2.getVisitLongitude(),o2.getVisitLatitude(), locatinLte, locationAte);
                            int diff = new BigDecimal(double1).compareTo(new BigDecimal(double2));
                            if(diff > 0) {
                                return 1;
                            } else if(diff < 0 ) {
                                return -1;
                            }
                            return 0;
                        }
            });

        }

    }

    public String dealTimeData(String time) {

        if (time.length() > 15) {
            return time;
        }
        return time + " " + "12:00:00";

    }

    public static void main(String[] args) {

        String time = "2010-10-10 12";

        System.out.println(time.substring(0,10));
        System.out.println(time.length());
    }


}
