package com.quantchi.tianji.service.search.service.sign.impl;

import com.quantchi.tianji.service.search.dao.*;
import com.quantchi.tianji.service.search.enums.JobEnum;
import com.quantchi.tianji.service.search.model.SignRecord;
import com.quantchi.tianji.service.search.model.UserInfo;
import com.quantchi.tianji.service.search.model.VisitRecord;
import com.quantchi.tianji.service.search.model.database.VisitRecordDTO;
import com.quantchi.tianji.service.search.model.vo.sign.*;
import com.quantchi.tianji.service.search.service.DingService;
import com.quantchi.tianji.service.search.utils.DateUtils;
import com.quantchi.tianji.service.search.utils.DistanceUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

import static java.math.BigDecimal.*;

/**
 * @Description 
 * @author leiel
 * @Date 2019/12/11 10:46 AM
 */
@Slf4j
@Service
public class SignDealService {

    @Resource
    private SignRecordMapper signRecordMapper;

    @Resource
    private InvUserDetailMapper invUserDetailMapper;

    @Resource
    private SignServiceImpl signServiceImpl;

    @Resource
    private ReceptionistInfoMapper receptionistInfoMapper;

    @Resource
    private VisitRecordMapper visitRecordMapper;

    @Resource
    private SignRecordSerivce signRecordSerivce;

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private UserDao userDao;

    @Value("${pic.url}")
    private String picUrl;

    /**
     * 签到率排序
     * @param groupSignVOS
     */
    public void signRateSort (List<GroupSignVO> groupSignVOS ) {

        if (CollectionUtils.isNotEmpty(groupSignVOS)) {
            Collections.sort(groupSignVOS, new Comparator<GroupSignVO>() {
                @Override
                public int compare(GroupSignVO o1, GroupSignVO o2) {
                    int diff = o1.getGroupSignRate().compareTo(o2.getGroupSignRate());
                    if(diff > 0) {
                        return -1;
                    } else if(diff < 0 ) {
                        return 1;
                    }
                    return 0;
                }
            });
        }

    }

    /**
     * 平均距离排序
     * @param groupSignVOS
     */
    public void signDistanceSort (List<GroupSignVO> groupSignVOS ) {

        if (CollectionUtils.isNotEmpty(groupSignVOS)) {
            Collections.sort(groupSignVOS, new Comparator<GroupSignVO>() {
                @Override
                public int compare(GroupSignVO o1, GroupSignVO o2) {
                    int diff = o1.getGroupSignDistance().compareTo(o2.getGroupSignDistance());
                    if(diff > 0) {
                        return -1;
                    } else if(diff < 0 ) {
                        return 1;
                    }
                    return 0;
                }
            });
        }

    }


    public void dealColumnData(GroupSignVO groupSignVO, String groupId, int totalDay, String startTime, String endTime) {

        List<UserVO> userVOList = new ArrayList<>();

        //获取当前组下的所有成员
        List<String> userList = invUserDetailMapper.queryStaffId(groupId);
        log.info("开始获取当前用户的所有成员信息userList={}",userList);

        //获取每位成员此时间段的签到情况并记录该组员哪天未签到
        for (String staffId : userList) {
            dealSignDataStaff(staffId, userVOList, totalDay, startTime, endTime);
        }
        groupSignVO.setUserVOList(userVOList);

    }

    public void dealSignDataStaff(String staffId, List<UserVO> userVOList, int totalDay, String startTime, String endTime) {

        UserVO userVO = new UserVO();
        List<Integer> signList = new ArrayList<>();

        //时间格式转换
        Date start = DateUtils.getDayAm(DateUtils.getDateYYYYMMddHHMMSS(startTime));
        Date end = DateUtils.getDayPm(DateUtils.getDateYYYYMMddHHMMSS(endTime));

        List<String> list = signRecordMapper.selectByStaffForSign(staffId, start, end);

        //遍历list判断用户签到情况 签到则存入1，否则存入0
        String staTime = startTime;
        for(int i = 0; i < totalDay; i++) {
            if(list.contains(DateUtils.DateChangeType(staTime))) {
                signList.add(1);
            }else {
                signList.add(0);
            }
            staTime = DateUtils.DateDayChange(staTime);

        }
        UserInfo userInfo = invUserDetailMapper.getUserInfoByStaffId(staffId);

        userVO.setStaffName(userInfo.getStaffName());
        userVO.setStaffJob(JobEnum.getDescByCode(userInfo.getStaffJob()));
        userVO.setStaffId(userInfo.getStaffId());
        userVO.setStaffImg(StringUtils.isBlank(userInfo.getStaffImgUrl())? null : picUrl + userInfo.getStaffImgUrl());
        userVO.setSignList(signList);
        userVO.setStaffGroup(userInfo.getStaffGroup());
        userVO.setGroupLocRegion(userInfo.getRegion());
        userVOList.add(userVO);
    }

    public void dealChartData(GroupSignVO groupSignVO, String groupId, int totalDay, String startTime, String endTime) {

        List<UserVO> userVOList = new ArrayList<>();

        startTime = signServiceImpl.dealTimeData(startTime);
        endTime = signServiceImpl.dealTimeData(endTime);

        //总签到次数
        int signCount = 0;

        //总行程
        BigDecimal distanceCount = BigDecimal.ZERO;

        //总拜访数
        int visitCount = 0;

        //总接待人
        int receCount = 0;

        //组时间段签到list
        List<BigDecimal> groupSignList = new ArrayList<>();

        //组时间段行程数list
        List<BigDecimal> groupDistanceList = new ArrayList<>();

        //组时间段拜访数list
        List<BigDecimal> groupVisitList = new ArrayList<>();

        //时间格式转换
        Date start = DateUtils.getDayAm(DateUtils.getDateYYYYMMddHHMMSS(startTime));
        Date end = DateUtils.getDayPm(DateUtils.getDateYYYYMMddHHMMSS(endTime));

        String staTime = startTime;
        if (startTime.length() > 15) {
            staTime = startTime.substring(0,10);
        }

        //获取签到总数
        List<VisitRecordDTO> listSign = signRecordMapper.getVisitByGroup(groupId, null, start, end, null);

        //获取行程总数和拜访总数
        List<VisitRecordDTO> list = signRecordMapper.getVisitByGroup(groupId, null, start, end, 1);

        //获取当前组下的所有成员
        List<String> userList = invUserDetailMapper.queryStaffId(groupId);

        int j = 0;
        int k = 0;
        for (int i = 0; i < totalDay; i++) {

            groupDistanceList.add(i, BigDecimal.ZERO);
            groupVisitList.add(i, BigDecimal.ZERO);
            groupSignList.add(i, BigDecimal.ZERO);

            if(CollectionUtils.isNotEmpty(list) && (j < list.size()) && DateUtils.DateChangeType(staTime).equals(list.get(j).getTimeNow())) {
                groupDistanceList.set(i, BigDecimal.valueOf(list.get(j).getDistance()));
                groupVisitList.set(i, new BigDecimal(list.get(j).getCout()));
                visitCount += list.get(j).getCout();
                distanceCount = distanceCount.add(BigDecimal.valueOf(list.get(j).getDistance()));
                j++;
            }

            if (CollectionUtils.isNotEmpty(listSign) && (k < listSign.size()) && DateUtils.DateChangeType(staTime).equals(listSign.get(k).getTimeNow())) {
                groupSignList.set(i, new BigDecimal(listSign.get(k).getCout()));
                signCount += listSign.get(k).getCout();
                k++;
            }

            staTime = DateUtils.DateDayChange(staTime);
        }
        //处理人均次数
        //dealAvgListSign(groupSignList, userList, totalDay);
        dealAvgList(groupVisitList, userList);
        dealAvgList(groupDistanceList, userList);

        //计算总的接待人
        receCount = calReceTotal(userList);

        groupSignVO.setGroupReceiveTotal(receCount);

        groupSignVO.setGroupSignTotal(signCount);
        groupSignVO.setGroupDistanceTotal(distanceCount.setScale(1, BigDecimal.ROUND_DOWN));
        groupSignVO.setGroupVisitTotal(visitCount);
        groupSignVO.setGroupSignList(groupSignList);
        groupSignVO.setGroupDistanceList(groupDistanceList);
        groupSignVO.setGroupVisitList(groupVisitList);
        if(CollectionUtils.isNotEmpty(userList)) {
            groupSignVO.setGroupVisitCount(new BigDecimal(visitCount).divide(new BigDecimal(userList.size()), 1, BigDecimal.ROUND_DOWN));
        }

    }

    private void dealAvgList(List<BigDecimal> totalList, List<String> userList) {

        for(int i = 0; i < totalList.size()-1; i++) {

            if(userList.size() == 0) {
                totalList.set(i, BigDecimal.ZERO);
            }
            totalList.set(i, totalList.get(i).divide(new BigDecimal(userList.size()),1, BigDecimal.ROUND_DOWN));

        }

    }

    private void dealAvgListSign(List<BigDecimal> totalList, List<String> userList, int totalDay) {

        for(int i = 0; i < totalList.size()-1; i++) {

            if(userList.size() == 0) {
                totalList.set(i, BigDecimal.ZERO);
            }
            totalList.set(i, totalList.get(i).divide(new BigDecimal(userList.size() * totalDay),3, BigDecimal.ROUND_DOWN));

        }

    }

    private Integer calReceTotal(List<String> userList) {

        Integer receCount = 0;
        if(CollectionUtils.isNotEmpty(userList)) {
            //掉接待人总数
            for(String id : userList) {
                Integer count = receptionistInfoMapper.coutByStaffId(id);

                if (count == null) {
                    continue;
                }

                receCount += count;
            }
        }
        return receCount;
    }


    public void dealVisitData(GroupSignVO groupSignVO, String groupId, int totalDay, String startTime, String endTime) {

        //获取当前组下的所有成员
        List<String> userList = invUserDetailMapper.queryStaffId(groupId);

        List<UserVO> userVOList = new ArrayList<>();

        for (String staffId : userList) {

            String startTimeNew = startTime;
            List<RouteVO> routeVOList = new ArrayList<>();
            UserVO userVO = new UserVO();
            for (int i = 0; i < totalDay; i++) {
                //时间格式转换
                Date start = DateUtils.getDayAm(DateUtils.getDateYYYYMMddHHMMSS(startTimeNew));
                Date end = DateUtils.getDayPm(DateUtils.getDateYYYYMMddHHMMSS(startTimeNew));

                dealVisitDataDay(userVO, routeVOList, staffId, start, end);

                //日期加一天
                startTimeNew = DateUtils.DateDayChangeNew(startTimeNew);

            }
            userVOList.add(userVO);
        }
        groupSignVO.setUserVOList(userVOList);

    }

    public void dealChartDataForStaff(StaffSignVO staffSignVO, String staffId, int totalDay, String startTime, String endTime) {

        List<UserVO> userVOList = new ArrayList<>();

        //总签到次数
        int signCount = 0;

        //总行程
        BigDecimal distanceCount = BigDecimal.ZERO;

        //总拜访数
        int visitCount = 0;

        //总拜访数
        int receCount = 0;

        //时间格式转换
        Date start = DateUtils.getDayAm(DateUtils.getDateYYYYMMddHHMMSS(startTime));
        Date end = DateUtils.getDayPm(DateUtils.getDateYYYYMMddHHMMSS(endTime));
        String staTime = startTime;

        //获取签到总数
        List<VisitRecordDTO> listSign = signRecordMapper.getVisitByGroup(null, staffId, start, end, null);

        //获取行程总数和拜访总数
        List<VisitRecordDTO> list = signRecordMapper.getVisitByGroup(null, staffId, start, end, 1);

        int j = 0;
        int k = 0;
        for (int i = 0; i <= totalDay; i++) {
            if(CollectionUtils.isNotEmpty(list) && (j < list.size()) && list.get(k) != null && DateUtils.DateChangeType(staTime).equals(list.get(k).getTimeNow())) {
                visitCount += list.get(k).getCout();
                distanceCount = distanceCount.add(BigDecimal.valueOf(list.get(k).getDistance()));
                k++;
            }

            if (CollectionUtils.isNotEmpty(listSign) && (j < listSign.size()) && listSign.get(j) != null && DateUtils.DateChangeType(staTime).equals(listSign.get(j).getTimeNow())) {
                signCount += listSign.get(j).getCout();
                j++;
            }

            staTime = DateUtils.DateDayChange(staTime);
        }
        List<String> userList = new ArrayList<>();
        userList.add(staffId);
        receCount = calReceTotal(userList);

        staffSignVO.setStaffReceiveTotal(receCount);
        staffSignVO.setStaffSignTotal(signCount);
        staffSignVO.setStaffDistanceTotal(distanceCount.setScale(1, BigDecimal.ROUND_DOWN));
        staffSignVO.setStaffVisitTotal(visitCount);

    }

    public void dealVisitDataDay(UserVO userVO, List<RouteVO> routeVOList, String staffId, Date start, Date end) {

        //获取时间段内当前用户所有拜访记录
        List<SignRecord> signRecords = signRecordMapper.selectByStaff(staffId,null, start, end);

        for (int j = 0; j< signRecords.size()-1; j++) {

            RouteVO routeVO = new RouteVO();
            routeVO.setStartPoint(signRecords.get(j));
            routeVO.setEndPoint(signRecords.get(j+1));
            routeVOList.add(routeVO);

        }
        userVO.setRouteVOList(routeVOList);
    }

    public void dealVisitByStaffId(UserVO userVO, UserInfo userInfo, String staffId, int totalDay, Date start, Date end) {

        //获取时间段内当前用户所有拜访记录
        dealSignRecordByStaffId(userVO, userInfo, staffId, start, end);

        String newTime = DateUtils.changeFormatDateToSec(start);

        start = DateUtils.getDateYYYYMMddHHMMSS(DateUtils.getFirstDayDateOfMonth(newTime));
        end = new Date();

        List<String> signList = signRecordMapper.selectByStaffForSign(staffId, start, end);

        userVO.setSignRate(new BigDecimal(signList.size()).divide(new BigDecimal(totalDay), 3, ROUND_UP));

    }

    public void dealSignRecordByStaffId(UserVO userVO, UserInfo userInfo, String staffId, Date start, Date end) {

        //获取staffId对应的签到数据(当日)
        List<SignRecord> signList = signRecordMapper.selectByStaff(staffId,null, start, end);
        //封装获取参数
        List<VisitParam> visitParams = new ArrayList<>();

        //数据转换
        dealSignData(signList, visitParams);

        //距离
        Double doubleDistance = signRecordMapper.getDistanceByStaff(staffId, start, end);

        if(doubleDistance != null) {
            userVO.setDiatanceToday(new BigDecimal(doubleDistance.toString()).setScale(1,BigDecimal.ROUND_DOWN));
        }

        userVO.setHourToday(BigDecimal.ZERO);
        if (signList.size() > 1) {
            long l = signList.get(signList.size()-1).getCreateTime().getTime() - signList.get(0).getCreateTime().getTime();
            long day = l / (24 * 60 * 60 * 1000);
            long hour = (l / (60 * 60 * 1000) - day * 24);
            long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

            userVO.setHourToday(new BigDecimal(hour).add(new BigDecimal((min*60+s)).divide(new BigDecimal("3600"), 1, BigDecimal.ROUND_UP)));
        }

        userVO.setStaffName(userInfo.getStaffName());
        userVO.setVisitRecord(visitParams);
        userVO.setSignTimes(signList.size());
        userVO.setStaffGroup(userInfo.getStaffGroup());
        userVO.setGroupLocRegion(userInfo.getRegion());
        userVO.setStaffJob(JobEnum.getDescByCode(userInfo.getStaffJob()));

    }

    /**
     * 数据转换
     * @param signList
     * @param visitParams
     */
    private void dealSignData(List<SignRecord> signList, List<VisitParam> visitParams) {

        if (CollectionUtils.isNotEmpty(signList)) {

            for(SignRecord signRecord : signList) {
                VisitParam visitParam = new VisitParam();
                visitParam.setVisitLocation(signRecord.getVisitLocation());
                visitParam.setVisitAte(signRecord.getVisitLatitude().toString());
                visitParam.setVisitLte(signRecord.getVisitLongitude().toString());
                visitParam.setVisitTime(signRecord.getCreateTime());
                visitParam.setVisitFlag(signRecord.getVisitFlag());
                visitParams.add(visitParam);
            }

        }

    }

    public void addDistanceDeal(UserVO userVO) {

        Integer result = visitRecordMapper.fetchCountByStaffIdNew(userVO.getStaffId(), 0, null, null);

        if(result == null || result == 0) {
            //需弹窗
            userVO.setAddDistance(1);
        }

    }

    public Integer dealSignAfter(String staffId, String visitId, String visitLocation, Double visitAte, Double visitLte) {
        //数据插入并返回用户签到数据及次数
        Integer result = signRecordSerivce.insertSignRecord(staffId, visitId, visitLocation,visitAte,visitLte);

        if(null == result) {
            return result;
        }

        //新启一个线程更新该用户当前里程数
        storeDataToSignRecord(staffId, visitAte, visitLte);

        //签到完成修改用户拜访记录数据 并将发起人拜访记录修改为已拜访
        if (null != visitId) {
            //获取同行人数据
            VisitRecord visitRecord = visitRecordMapper.selectByPrimaryKey(visitId);

            //masterId为空则为发起人行程 校验是否有同行人更新全部同行人数据
            if(visitRecord.getMasterId() == null) {
                //visitRecordMapper.updateById(visitId, new Date());

                if(StringUtils.isNotBlank(visitRecord.getVisitPartner())) {
                    //更新全部相关数据
                    updatePartnerVisitData(visitRecord);
                }

            }else {
                //masterId不为空则为同行人之一行程
                VisitRecord visitRe = visitRecordMapper.selectByPrimaryKey(visitRecord.getMasterId());

                //更新主行程
               // visitRecordMapper.updateById(visitRecord.getMasterId(), new Date());

                updatePartnerVisitData(visitRe);
            }

        }

        return result;
    }

    private void updatePartnerVisitData(VisitRecord visitRecord) {
        String partner = visitRecord.getVisitPartner();
        String[] partners = partner.split(",");
        for (String par : partners) {
            //根据姓名获取staffId
            String staff = userDao.getStaffIdByName(par);
            //VisitRecord visit = visitRecordMapper.selectByMasterId(visitRecord.getId(), staff, 1);
            //visitRecordMapper.updateById(visit.getId(), new Date());
        }
    }

    public void storeDataToSignRecord(final String staffId,final Double visitAte, final Double visitLte) {

        log.info("异步更新signRecord信息开始----");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    SignRecord signRecord = new SignRecord();
                    //查询今日是否有签到数据
                    List<SignRecord> signList = signRecordMapper.selectByStaff(staffId,null, DateUtils.getDayAm(new Date()),DateUtils.getDayPm(new Date()));

                    //计算上次与这次的经纬度的距离
                    if(signList.size() > 1) {
                        signRecord.setDistanceTotal(DistanceUtils.getDistance(signList.get(signList.size()-2).getVisitLongitude(),
                                signList.get(signList.size()-2).getVisitLatitude(), visitLte, visitAte));
                    }else {
                        signRecord.setDistanceTotal(0.00);
                    }

                    signRecord.setId(signList.get(signList.size()-1).getId());

                    signRecord.setSignTimes(signList.size());

                    UserInfo userInfo = userInfoService.selectUserInfo(staffId);
                    signRecord.setStaffGroup(userInfo.getStaffGroup());

                    signRecordMapper.updateById(signRecord);
                }catch (Exception e) {
                    log.error("查询规则数据失败, e={}", e);
                }
                log.info("异步更新signRecord信息结束----");
            }
        }).start();
    }

    public void dealGroup(List<String> listGroup) {

        if(CollectionUtils.isEmpty(listGroup)) {
            return;
        }

        Iterator<String> it = listGroup.iterator();
        while(it.hasNext()) {
            String group = it.next();
            if(StringUtils.isBlank(group)) {
                it.remove();
            }
        }
    }

}
