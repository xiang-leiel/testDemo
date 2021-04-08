package com.example.test.algorithm.leeCode.dynamic;

import jodd.util.ArraysUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/diving-board-lcci/
 * @Description 
 * @author leiel
 * @Date 2020/7/8 10:31 AM
 */

public class _跳水板 {

    /**
     * 输入：
     * shorter = 1
     * longer = 2
     * k = 3
     * 输出： {3,4,5,6}
     * @param shorter
     * @param longer
     * @param k
     * @return
     */
    public static int[] divingBoard(int shorter, int longer, int k) {

        List<Integer> list = new ArrayList<>();

        if(k == 0) return new int[0];

        //可采用枚举法 获取所有的再排序
        int n = 0;

        for (int i = 0; i <= k; i++) {

            int m = k-i;

            int value = i*shorter + m*longer;
            if(!list.contains(value)) {
                list.add(value);
            }

        }

        Collections.sort(list);

        int[] result = new int[list.size()];


        for(int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }

        return result;

    }

    public static int[] divingBoardTwo(int shorter, int longer, int k) {

        if(k == 0) return new int[0];

        if (shorter == longer) {
            return new int[]{shorter * k};
        }

        int[] result = new int[k+1];

        //可采用枚举法 获取所有的再排序
        for (int n = k; n >= 0; n--) {

            int m = k-n;

            result[m] = shorter * n + longer * m;

        }

        return result;

    }

    public static void main(String[] args) {

        System.out.println(divingBoard(2, 1118596, 979));

    }

}
