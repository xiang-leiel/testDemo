package com.quantchi.tianji.service.search.interfaces.http;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.service.FollowingCompanyEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**关注企业事件
 * @author whuang
 * @date 2019/7/13
 */
@RestController
@RequestMapping(value = "/following/company")
public class FollowingCompanyEventController {

    @Autowired
    private FollowingCompanyEventService followingCompanyEventService;

    @GetMapping(value = "/event",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public String event(String staffId,String keyword,Integer page, Integer pageSize) {
        ResultInfo resultInfo = followingCompanyEventService.listFollowingCompanyEvent(staffId,keyword,page,pageSize);
        return JSONObject.toJSONString(resultInfo, SerializerFeature.WriteMapNullValue);
    }
}
