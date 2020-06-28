package com.quantchi.tianji.service.search.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 
 * @author leiel
 * @Date 2020/1/13 5:01 PM
 */

public class ListPageUtil {

    /**
     *
     * @param page 当前页数
     * @param pageSize 每页得大小
     * @param list 分页的对象
     * @return
     */
    public static <T> List<T> getListPage(int page, int pageSize, List<T> list) {
        if (list == null || list.size() == 0) {
            throw new RuntimeException("分页数据不能为空!");
        }

        int totalCount = list.size();
        int fromIndex = page * pageSize;
        //分页不能大于总数
        if(fromIndex>=totalCount) {
            throw new RuntimeException("页数或分页大小不正确!");
        }
        int toIndex = ((page + 1) * pageSize);
        if (toIndex > totalCount) {
            toIndex = totalCount;
        }
        List<T> value = list.subList(fromIndex, toIndex);

        return value;

    }

    public static void main(String[] args) {
        // 构造100条数据
        List<String> list = new ArrayList<String>();
        for (Integer i = 1; i <= 100; i++) {
            list.add(""+i);
        }

        int page = 0;// 第一页
        int pageSize = 10;// 每页10条

        List<String> listPage = getListPage(page, pageSize, list);
        System.out.println("第" + page + "页");
        for (String integer : listPage) {
            System.out.println(integer);
        }

    }
}
