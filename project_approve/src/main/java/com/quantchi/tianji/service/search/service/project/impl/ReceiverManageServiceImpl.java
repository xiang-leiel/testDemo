package com.quantchi.tianji.service.search.service.project.impl;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.dao.ReceptionistEvaluateMapper;
import com.quantchi.tianji.service.search.dao.ReceptionistInfoMapper;
import com.quantchi.tianji.service.search.enums.ErrCode;
import com.quantchi.tianji.service.search.model.ReceiverParam;
import com.quantchi.tianji.service.search.model.ReceptionistEvaluate;
import com.quantchi.tianji.service.search.model.ReceptionistInfo;
import com.quantchi.tianji.service.search.model.vo.EvaluateParam;
import com.quantchi.tianji.service.search.model.vo.ReceiverVO;
import com.quantchi.tianji.service.search.service.project.ProjectDealService;
import com.quantchi.tianji.service.search.service.project.ReceiverManageService;
import com.quantchi.tianji.service.search.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Description 
 * @author leiel
 * @Date 2019/12/18 4:47 PM
 */
@Slf4j
@Service
public class ReceiverManageServiceImpl implements ReceiverManageService {

    @Resource
    private ReceptionistInfoMapper receptionistInfoMapper;

    @Resource
    private ReceptionistEvaluateMapper receptionistEvaluateMapper;

    @Resource
    private ProjectDealService projectDealService;

    @Override
    public ResultInfo queryReceiverByVisit(Long visitId) {

        Map<String, List<ReceiverVO>> receiverMap = new HashMap<>();

        //获取visitId下所有接待人信息
        List<ReceptionistInfo> receptionistInfoList = receptionistInfoMapper.selectByVisitId(visitId);

        if(CollectionUtils.isNotEmpty(receptionistInfoList)) {
            List<ReceiverVO> receptionList = new ArrayList<>();
            List<ReceiverVO> receptionedList = new ArrayList<>();

            for (ReceptionistInfo receptionistInfo : receptionistInfoList) {
                ReceiverVO receiverVO = new ReceiverVO();
                receiverVO.setPhotoUrl(receptionistInfo.getPhotoUrl());
                receiverVO.setName(receptionistInfo.getName());
                receiverVO.setJob(receptionistInfo.getJob());
                receiverVO.setIsEvaluate(0);
                receiverVO.setId(receptionistInfo.getId());
                ReceptionistEvaluate receptionistEvaluate = receptionistEvaluateMapper.selectById(receptionistInfo.getId().longValue());
                if (null != receptionistEvaluate) {
                    receiverVO.setIsEvaluate(1);
                    receptionList.add(receiverVO);
                } else {
                    receptionedList.add(receiverVO);
                }
            }
            receiverMap.put("reception", receptionList);
            receiverMap.put("receptioned", receptionedList);

        }
        return ResultUtils.success(receiverMap);
    }

    @Override
    public ResultInfo queryReceiver(Long receiverId) {

        ReceptionistInfo receptionistInfo = receptionistInfoMapper.selectByPrimaryKey(receiverId);

        return ResultUtils.success(receptionistInfo);
    }

    @Override
    public ResultInfo editReceiver(ReceiverParam receiverParam) {

        ReceptionistInfo receptionistInfoDTO = new ReceptionistInfo();

        converDto(receptionistInfoDTO, receiverParam);

        if(null == receiverParam.getReceiverId() && null != receiverParam.getVisitId()) {

            List<ReceptionistInfo> receptionistInfoList = receptionistInfoMapper.selectByVisitId(Long.valueOf(receiverParam.getVisitId()));

            if (receptionistInfoList.size() > 4) {
                return ResultUtils.fail(ErrCode.USER_OVER);
            }

            //新增
            receptionistInfoMapper.insertSelective(receptionistInfoDTO);
            return ResultUtils.success(receptionistInfoDTO.getId());
        } else {
            //更新
            receptionistInfoDTO.setId(receiverParam.getReceiverId());
            receptionistInfoDTO.setUpdateTime(new Date());
            receptionistInfoMapper.updateByPrimaryKeySelective(receptionistInfoDTO);
            ResultUtils.success(receiverParam.getReceiverId());
        }

        return ResultUtils.success(true);
    }

    @Override
    public ResultInfo evaluateReceiver(EvaluateParam evaluateParam) {

        ReceptionistEvaluate receptionistEvaluate = new ReceptionistEvaluate();

        BeanUtils.copyProperties(evaluateParam, receptionistEvaluate);
        receptionistEvaluate.setId(evaluateParam.getReceiverId());

        receptionistEvaluateMapper.insertSelective(receptionistEvaluate);

        //查看是否需将项目状态改为待上报  是否已完成签到、评价和记录
        //projectDealService.updateProjectStatus(evaluateParam.getVisitId().intValue());

        return ResultUtils.success(true);
    }

    @Override
    public ResultInfo queryReceiverEvaluate(Long receiverId) {

        ReceptionistEvaluate receptionistEvaluate = receptionistEvaluateMapper.selectById(receiverId);

        if (receptionistEvaluate == null) {
            return ResultUtils.fail(ErrCode.NOT_EXIST.getCode(),"评价信息不存在");
        }

        String university = receptionistEvaluate.getUnivesity();
        if(null != university && university.length() > 4) {
            String str = university.substring(university.length()-4, university.length());
            try {
                int value = Integer.valueOf(str);
                receptionistEvaluate.setUnivesity(university+"年毕业");
            }catch (Exception e) {
                log.error("不能转Integer类型{}",receptionistEvaluate.getId());
            }

        }

        return ResultUtils.success(receptionistEvaluate);
    }

    private void converDto(ReceptionistInfo record, ReceiverParam receiverParam) {

        record.setName(receiverParam.getName());
        record.setJob(receiverParam.getJob());
        record.setSex(receiverParam.getSex());
        record.setOriginType(receiverParam.getOriginType());
        record.setOriginName(receiverParam.getOriginName());
        record.setInterrelateName(receiverParam.getInterrelateName());
        record.setMobile(receiverParam.getMobile());
        record.setWechat(receiverParam.getWechat());
        record.setEmail(receiverParam.getEmail());
        record.setWorkAddress(receiverParam.getWorkAddress());
        record.setPhotoUrl(receiverParam.getPhotoUrl());
        record.setStaffId(receiverParam.getStaffId());
        record.setVisitId(Long.valueOf(receiverParam.getVisitId()));

    }
}
