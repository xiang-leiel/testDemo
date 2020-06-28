package com.quantchi.tianji.service.search.service.sign.impl;

import com.quantchi.tianji.service.search.dao.SignRecordMapper;
import com.quantchi.tianji.service.search.dao.VisitRecordMapper;
import com.quantchi.tianji.service.search.model.SignRecord;
import com.quantchi.tianji.service.search.model.VisitRecord;
import com.quantchi.tianji.service.search.utils.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Description 
 * @author leiel
 * @Date 2019/12/11 10:26 AM
 */
@Service
public class SignRecordSerivce {

    @Resource
    private SignRecordMapper signRecordMapper;

    public Integer insertSignRecord(String staffId, String visitId, String visitLocation, Double visitAte, Double visitLte) {

        SignRecord signRecord = new SignRecord();
        signRecord.setStaffId(staffId);
        signRecord.setCreateTime(new Date());
        signRecord.setVisitLocation(visitLocation);
        signRecord.setVisitLatitude(visitAte);
        signRecord.setVisitLongitude(visitLte);

        //查询今日是否已签到
        SignRecord signRecord1 = new SignRecord();
        if (null == visitId) {
            signRecord1 = signRecordMapper.selectOne(null, staffId, visitLocation, DateUtils.getDayAm(new Date()), DateUtils.getDayPm((new Date())));

        }else {
            //查询行程记录表 关联id
            //signRecord.setVisitId(visitId);
            signRecord.setVisitFlag(1);
            signRecord1 = signRecordMapper.selectByVisitId(visitId, staffId, DateUtils.getDayAm(new Date()), DateUtils.getDayPm((new Date())));
        }

        if (null != signRecord1) {
            return null;
        }

        return signRecordMapper.insertSelective(signRecord);
    }

}
