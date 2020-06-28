package com.quantchi.tianji.service.search.service.upload.impl;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.dao.ProjectRecordMapper;
import com.quantchi.tianji.service.search.dao.ReceptionistInfoMapper;
import com.quantchi.tianji.service.search.model.ProjectRecord;
import com.quantchi.tianji.service.search.model.ReceptionistInfo;
import com.quantchi.tianji.service.search.service.impl.BusinessCardServiceImpl;
import com.quantchi.tianji.service.search.service.upload.UploadImgService;
import com.quantchi.tianji.service.search.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description 
 * @author leiel
 * @Date 2020/1/2 10:51 AM
 */
@Slf4j
@Service
public class UploadImgServiceImpl implements UploadImgService {

    @Resource
    private BusinessCardServiceImpl businessCardService;

    @Resource
    private ReceptionistInfoMapper receptionistInfoMapper;

    @Resource
    private ProjectRecordMapper projectRecordMapper;


    @Value("${pic.url}")
    private String picUrl;

    @Override
    public ResultInfo uploadImgByType(MultipartFile uploadImage, Map<String, Object> condition) {

        //上传类型 0:接待人头像, 1:接待人名片, 2:行程记录
        int type = (int) condition.get("type");

        String name = uploadImage.getOriginalFilename();
        String ext = name.substring(name.lastIndexOf(".")+1);
        Timestamp timestamp= Timestamp.valueOf(LocalDateTime.now());
        String fileName = String.valueOf(timestamp.getTime()) ;
        fileName = fileName+"."+ext;
        String filePath = "./pic/reception/";
        String url = picUrl + "/pic/reception/"+fileName;
        if(type == 2) {
            filePath = "./pic/visitPhoto/";
            url = picUrl + "/pic/visitPhoto/"+fileName;
        }

        try {
            businessCardService.fileUpload(uploadImage.getBytes(), filePath, fileName);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        switch (type) {
            case 0 :
                Long receId = updatePreferenceInfo(url, condition);
                return ResultUtils.success(receId);
            case 1 :
                updatePreferenceCard(url, condition);
            case 2 :
                updateVisitImg(url, condition);
                return ResultUtils.success("success");
        }

        return ResultUtils.success("success");
    }

    private Long updatePreferenceInfo(String url, Map<String, Object> condition) {

         Long receiverId = (Long)condition.get("receiverId");

        if (null == receiverId) {
            Long visitId = (Long)condition.get("visitId");
            ReceptionistInfo receptionistInfo = new ReceptionistInfo();
            receptionistInfo.setPhotoUrl(url);
            receptionistInfo.setVisitId(visitId);
            receptionistInfoMapper.insertSelective(receptionistInfo);
            return receptionistInfo.getId();
        } else {
            ReceptionistInfo receptionistInfo = new ReceptionistInfo();
            receptionistInfo.setId(receiverId);
            receptionistInfo.setPhotoUrl(url);
            receptionistInfoMapper.updateByPrimaryKeySelective(receptionistInfo);
        }

        return null;
    }

    private void updatePreferenceCard(String url, Map<String, Object> condition) {

        Long receiverId = (Long)condition.get("receiverId");
        ReceptionistInfo receptionistInfo = new ReceptionistInfo();
        receptionistInfo.setId(receiverId);
        receptionistInfo.setCardId(url);
        receptionistInfoMapper.updateByPrimaryKeySelective(receptionistInfo);

    }

    private void updateVisitImg(String url, Map<String, Object> condition) {

        Long visitId = (Long)condition.get("visitId");
        //一个项目只有一条projectRecord
        ProjectRecord projectRecord = null;
        //projectRecordMapper.selectById(visitId);

        if (null == projectRecord) {
            ProjectRecord project = new ProjectRecord();
            project.setReceivePhoto(url);
            projectRecordMapper.insertSelective(project);
            return;
        }

        StringBuffer sb = new StringBuffer();
        if(null != projectRecord.getReceivePhoto()) {
            sb.append(projectRecord.getReceivePhoto()+",");
        }

        sb.append(url);
        //projectRecordMapper.updateForPhotoUrl(visitId, sb.toString());

        return;

    }
}
