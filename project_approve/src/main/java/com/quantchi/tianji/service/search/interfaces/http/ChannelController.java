package com.quantchi.tianji.service.search.interfaces.http;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.model.Channel;
import com.quantchi.tianji.service.search.service.ChannelSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: DeQing-InvestmentEnterprise
 * @description:
 * @author: mf
 * @create: 2019-09-09 15:53
 **/
@RestController
@RequestMapping("/channel")
@Slf4j
public class ChannelController {

    @Autowired
    private ChannelSearchService channelSearchService;

    /**
     * 渠道会议搜索
     * @param channel
     * @return
     */
    @PostMapping("/search")
    public ResultInfo searchCompany(@RequestBody Channel channel) {
        ResultInfo resultInfo = channelSearchService.search(channel);
        return resultInfo;
    }


    @GetMapping("/index")
    public ResultInfo index(String sort) {
        ResultInfo resultInfo = channelSearchService.index(sort);
        return resultInfo;
    }

    /**
     * 招商员渠道会议消息
     * @param staffId
     * @return
     */
    @GetMapping("/normalMessage")
    public ResultInfo normalChannelMessage(String staffId, Integer page, Integer pageSize) {
        ResultInfo resultInfo = channelSearchService.normalChannelMessage(staffId, page, pageSize);
        return resultInfo;
    }

    /**
     * 招商领导渠道会议消息
     * @param staffId
     * @return
     */
    @GetMapping("/leadMessage")
    public ResultInfo leadChannelMessage(String staffId, Integer page, Integer pageSize) {
        ResultInfo resultInfo = channelSearchService.leadChannelMessage(staffId, page, pageSize);
        return resultInfo;
    }
}
