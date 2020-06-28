package com.quantchi.tianji.service.search.interfaces.http;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.model.DynamicSearchParam;
import com.quantchi.tianji.service.search.model.SearchParam;
import com.quantchi.tianji.service.search.model.menu.LabelMenu;
import com.quantchi.tianji.service.search.model.param.ProjectSearchParams;
import com.quantchi.tianji.service.search.service.catalog.SearchCatalogService;
import com.quantchi.tianji.service.search.service.project.ProjectManageService;
import com.quantchi.tianji.service.search.utils.ResultUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Description 动态标签查询口径controller
 * @author leiel
 * @Date 2020/2/20 5:29 PM
 */
@CrossOrigin
@RestController
@RequestMapping("/catalog")
public class CatalogController {

    @Resource
    private SearchCatalogService searchCatalogService;

    /**
     * 动态标签查询口径controller
     * pageId pageId代表的是哪个页面
     * @return
     */
    @GetMapping("/searchLabels")
    public ResultInfo searchLabels(int pageId) {

        List<LabelMenu> labelMenuList = searchCatalogService.getCatalogItem(pageId);

        return ResultUtils.success(labelMenuList);
    }

    /**
     * 动态标签查询项目数据
     * pageId pageId代表的是哪个页面
     * @return
     */
    @PostMapping("/searchProject")
    public ResultInfo queryProjectOne(@RequestBody SearchParam searchParam) {

        ResultInfo resultInfo = searchCatalogService.queryProjectList(searchParam);

        return resultInfo;
    }

}
