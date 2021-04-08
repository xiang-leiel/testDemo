package com.example.test.algorithm.leeCode.array;

import com.example.test.utils.SortUtils;

/**
 * https://leetcode-cn.com/problems/sub-sort-lcci/
 * @Description 部分排序
 * @author leiel
 * @Date 2020/5/18 8:51 AM
 */
public class PartSort {

    /**
     * //循环两遍  左一趟 又一趟  双指针
     * [1,2,4,7,10,11,7,12,6,7,16,18,19]
     */
    public static int[] subSort(int[] array) {

        int[] result = {-1, -1};

        if(array.length == 0) {
            return result;
        }

        int max = array[0];

        int right = 0;

        int left = 0;

        //第一遍找到最右边的位置  从左往右
        for(int i = 0; i < array.length; i++) {

            if(array[i] >= max) {
                max = array[i];
            }else {
                right = i;
            }

        }

        int min = array[right];

        left = right;

        //第二遍找到最左边的位置  从右往左
        for(int i = right; i >= 0; i--) {

            if(array[i] < min) {
                min = array[i];
            }else {
                left = i;
            }


        }

        if(right == 0) {
            return result;
        }

        result[0] = left;

        result[1] = right;

        SortUtils.print(result);

        return result;

    }

    public static void main(String[] args) {

        int[] array = {5,3,1,7,9};

        subSort(array);
    }



}
