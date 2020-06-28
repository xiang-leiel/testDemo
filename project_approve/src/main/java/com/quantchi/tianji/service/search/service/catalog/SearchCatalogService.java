package com.quantchi.tianji.service.search.service.catalog;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.dao.*;
import com.quantchi.tianji.service.search.model.*;
import com.quantchi.tianji.service.search.model.menu.LabelMenu;
import com.quantchi.tianji.service.search.model.vo.project.ProjectVO;
import com.quantchi.tianji.service.search.service.project.ProjectReportManageService;
import com.quantchi.tianji.service.search.utils.ResultUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Description 
 * @author leiel
 * @Date 2020/2/20 10:51 PM
 */
@Service
public class SearchCatalogService {

    @Resource
    private TQueryPageMapper tQueryPageMapper;

    @Resource
    private XmjbxxZbMapper xmjbxxZbMapper;

    @Resource
    private TQueryCatalogItemMapper tQueryCatalogItemMapper;

    public List<LabelMenu> getCatalogItem(int pageId) {

        List<TQueryCatalog> catalogList = tQueryPageMapper.selectListAll(pageId);

        List<LabelMenu> menuList = new ArrayList<>();

        for(TQueryCatalog tQueryCatalog : catalogList) {

            LabelMenu labelMenu = new LabelMenu();
            labelMenu.setMenuId(tQueryCatalog.getCatalogId().toString());
            labelMenu.setMenuName(tQueryCatalog.getCatalogName());
            labelMenu.setParentId(tQueryCatalog.getParentId() != null ? tQueryCatalog.getParentId().toString():null);
            labelMenu.setSort(tQueryCatalog.getSort());
            menuList.add(labelMenu);

        }

        for(TQueryCatalog tQueryCatalog : catalogList) {

            //查看当前节点是否为最后一级，如为最后一级则将数据
            List<TQueryCatalog> tQueryList = tQueryPageMapper.selectByCatalogId(tQueryCatalog.getCatalogId());
            //如果等于空，则说明为最后一级
            if(CollectionUtils.isEmpty(tQueryList)) {
                List<TQueryCatalogItem> itemList = tQueryCatalogItemMapper.selectListByCatalogId(tQueryCatalog.getCatalogId());

                for(TQueryCatalogItem tQueryCatalogItem : itemList) {
                    LabelMenu labelMenu = new LabelMenu();
                    labelMenu.setMenuId(tQueryCatalogItem.getItemId().toString());
                    labelMenu.setMenuName(tQueryCatalogItem.getItemName());
                    labelMenu.setParentId(tQueryCatalog.getCatalogId().toString());
                    menuList.add(labelMenu);
                }
            }

        }
        //根节点对象存放到这里
        List<LabelMenu> rootList = new ArrayList<>();
        //其他节点存放到这里，可以包含根节点
        List<LabelMenu> bodyList = new ArrayList<>();
        for (LabelMenu sysTagConf: menuList) {
            if(sysTagConf != null) {
                if (sysTagConf.getParentId() == null) {
                    //所有父节点数据放进去
                    rootList.add(sysTagConf);
                } else {
                    //其他节点数据也放进去
                    bodyList.add(sysTagConf);
                }
            }
        }

        //父节点排序
        Collections.sort(rootList, new Comparator<LabelMenu>() {
            @Override
            public int compare(LabelMenu o1, LabelMenu o2) {
                int diff = o1.getSort().compareTo(o2.getSort());
                if(diff > 0) {
                    return 1;
                } else if(diff < 0 ) {
                    return -1;
                }
                return 0;
            }
        });

        //组装的树返给前端
        List<LabelMenu> stc = getTree(rootList,bodyList);

        return stc;
    }


    public List<LabelMenu> getTree(List<LabelMenu> rootList,List<LabelMenu> bodyList){   //调用的方法入口
        if(bodyList != null && !bodyList.isEmpty()){
            //声明一个map，用来过滤已操作过的数据
            Map<String,String> map = Maps.newHashMapWithExpectedSize(bodyList.size());
            rootList.forEach(beanTree -> getChild(beanTree,map,bodyList));
            return rootList;
        }
        return null;
    }

    public void getChild(LabelMenu treeDto,Map<String,String> map,List<LabelMenu> bodyList){
        List<LabelMenu> childList = Lists.newArrayList();
        bodyList.stream()
                .filter(c -> !map.containsKey(c.getMenuId()))
                .filter(c ->c.getParentId().equals(treeDto.getMenuId()))
                .forEach(c ->{
                    map.put(c.getMenuId(),c.getParentId());
                    getChild(c, map, bodyList);
                    childList.add(c);
                });
        treeDto.setChildMenu(childList);

    }

    @Resource
    private XmGlTzgmMapper xmGlTzgmMapper;

    @Resource
    private XmGlBqlyMapper xmGlBqlyMapper;

    @Resource
    private ProjectReportManageService projectReportManageService;

    public ResultInfo queryProjectList(SearchParam searchParam) {

        List<DynamicSearchParam> params = searchParam.getDynamicSearchParam();

        ProjectVO projectVO = new ProjectVO();

        ProjectReportDTO projectReportDTO = new ProjectReportDTO();

        //获取全部数据
        List<XmjbxxZb> xmjbxxZbs = xmjbxxZbMapper.queryReportDataAll(projectReportDTO);

        Iterator<XmjbxxZb> it = xmjbxxZbs.iterator();

        if(CollectionUtils.isNotEmpty(params) && CollectionUtils.isNotEmpty(xmjbxxZbs)) {

            while(it.hasNext()) {
                XmjbxxZb xmjbxxZb = it.next();

                Boolean existFlag = false;
                for(DynamicSearchParam map : params) {

                    TQueryCatalog catalog = tQueryPageMapper.selectOneByCatalogId(map.getTopLabelId());
                    //如果关联投资规模
                    if(catalog.getFieldName().equals("tzgm_dm")) {
                        XmGlTzgm xmGlTzgm = xmGlTzgmMapper.selectOneByXmjbxxId(xmjbxxZb.getXmjbxxId());
                        if(map.getChildLabelId().contains(xmGlTzgm.getTzgmDm())) {
                            existFlag = true;
                        }
                    }else if(catalog.getFieldName().equals("bqly_dm")){
                        List<Integer> xmGlBqlyList = xmGlBqlyMapper.selectIdListByXmId(xmjbxxZb.getXmjbxxId());
                        if(!Collections.disjoint(map.getChildLabelId(),xmGlBqlyList)){
                            existFlag = true;
                        }
                    }else{
                        Boolean countFlag = false;
                        for(Integer value : map.getChildLabelId()) {
                            String sql = "select count(1) from xmjbxx_zb where xmjbxx_id = " + xmjbxxZb.getXmjbxxId()
                                    + "and " + catalog.getFieldName() + "=" + value;
                            Integer count = xmjbxxZbMapper.selectBySql(sql);

                            if(count != null && count > 0) {
                                countFlag = true;
                                break;
                            }
                        }
                        if(countFlag) {
                            existFlag = true;
                        }
                    }
                }
                if(!existFlag) {
                    it.remove();
                    continue;
                }

            }
            projectVO.setTotal(xmjbxxZbs.size());
            //项目数据转换
            projectReportManageService.turnToShowData(xmjbxxZbs, projectVO, searchParam.getUserId(), searchParam.getPage(), searchParam.getPageSize());

        }

        return ResultUtils.success(projectVO);
    }

}
