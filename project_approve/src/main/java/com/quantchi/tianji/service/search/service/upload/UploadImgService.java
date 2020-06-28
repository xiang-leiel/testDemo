package com.quantchi.tianji.service.search.service.upload;

import com.quantchi.core.message.ResultInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author leiel
 * @Description
 * @Date 2020/1/2 10:51 AM
 */

public interface UploadImgService {

    /**
     * 上传图片
     * @param uploadImage
     * @param condition 上传类型 0:接待人头像, 1:接待人名片, 2:行程记录
     * @return
     */
    ResultInfo uploadImgByType(MultipartFile uploadImage, Map<String, Object> condition);

}
