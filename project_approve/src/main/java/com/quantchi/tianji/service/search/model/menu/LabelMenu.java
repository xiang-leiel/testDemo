package com.quantchi.tianji.service.search.model.menu;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 
 * @author leiel
 * @Date 2020/2/20 11:19 PM
 */
@Data
public class LabelMenu {

    /**
     * 菜单id
     */
    private String menuId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 父级id
     */
    private String parentId;

    /**
     * 排序位置
     */
    private Integer sort;

    /**
     * 子菜单
     */
    private List<LabelMenu> childMenu = new ArrayList<>();

}
