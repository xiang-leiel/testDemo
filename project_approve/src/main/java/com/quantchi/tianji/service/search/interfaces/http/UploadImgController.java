package com.quantchi.tianji.service.search.interfaces.http;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.enums.ErrCode;
import com.quantchi.tianji.service.search.service.upload.UploadImgService;
import com.quantchi.tianji.service.search.utils.ResultUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * @Description 
 * @author leiel
 * @Date 2020/1/2 9:46 AM
 */
@Slf4j
@RestController
@RequestMapping("/upload")
public class UploadImgController {

    @Resource
    private UploadImgService uploadImgService;

    /**
     * 上传图片信息
     * @param uploadImage
     * @param type 上传类型 0:接待人头像, 1:接待人名片, 2:行程记录
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/image", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResultInfo uploadImage(@ApiParam(name = "uploadImage", value = "上传图片") MultipartFile uploadImage,
                                  @ApiParam(name = "visitId", value = "行程id") Long visitId,
                                  @ApiParam(name = "receiverId", value = "接待人id") Long receiverId,
                                  @ApiParam(name = "type", value = "上传类型") Integer type) {
        if (uploadImage == null) {
            return ResultUtils.fail(ErrCode.NOT_EXIST.getCode(), "用户上传的图片");
        }

        Map<String, Object> condition = new HashMap<>();
        condition.put("visitId", visitId);
        condition.put("receiverId", receiverId);
        condition.put("type", type);

        if(null == condition) {
            return ResultUtils.fail(ErrCode.NOT_NULL.getCode(), "上传类型");
        }
        ResultInfo resultInfo = uploadImgService.uploadImgByType(uploadImage, condition);

        return resultInfo;
    }

}
