package com.quantchi.tianji.service.search.interfaces.http;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.model.PageBean;
import com.quantchi.tianji.service.search.service.AssistantService;
import com.quantchi.tianji.service.search.utils.ResultUtils;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/assistant")
public class AssistantController {

    @Autowired
    private AssistantService assistantService;
    @GetMapping("/getClassifyInfo")
    public ResultInfo getClassifyInfo(String name) {
        List<Map<String, Object>> list = assistantService.getClassifyInfo(name);
        return ResultUtils.success(list);
    }

    @GetMapping("/getGroupInfo")
    public ResultInfo getGroupInfo() {
        List<String> list = assistantService.getGroupInfo();
        return ResultUtils.success(list);
    }

    @PostMapping("/addAssistantInfo")
    public ResultInfo addAssistantInfo(@RequestBody Map<String, Object> condition) {
        assistantService.addAssistantInfo(condition);
        return ResultUtils.success("success");
    }

    @GetMapping("/getAssistantInfo")
    public ResultInfo getAssistantInfo(String classify, String userId, Integer from, Integer size) {
        PageBean<Map<String, Object>> list =assistantService.getAssistantInfo(classify, userId, from, size);
        return ResultUtils.success(list);
    }

    @PostMapping("/addAssistantCommentInfo")
    public ResultInfo addAssistantCommentInfo(@RequestBody Map<String, Object> condition) {
        assistantService.addAssistantCommentInfo(condition);
        return ResultUtils.success("success");
    }
    @GetMapping("/getAssistantCommentInfo")
    public ResultInfo getAssistantCommentInfo(Integer assistantId, Integer from, Integer size) {
        PageBean<Map<String, Object>> list =assistantService.getAssistantCommentInfo(assistantId, from, size);
        return ResultUtils.success(list);
    }

    @PostMapping("/addAssistantAgreeInfo")
    public ResultInfo addAssistantAgreeInfo(@RequestBody Map<String, Object> condition) {
        assistantService.addAssistantAgreeInfo(condition);
        return ResultUtils.success("success");
    }

    @PostMapping("/getAssistantAgreeInfoByCondition")
    public ResultInfo getAssistantAgreeInfoByCondition(@RequestBody Map<String, Object> condition) {
        int flag =assistantService.getAssistantAgreeInfoByCondition(condition);
        return ResultUtils.success(flag);
    }

}
