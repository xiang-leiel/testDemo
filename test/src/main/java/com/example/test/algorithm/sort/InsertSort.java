package com.example.test.algorithm.sort;

import com.example.test.utils.SortUtils;

/**
 * @Description 插入排序
 * @author leiel
 * @Date 2020/4/9 3:19 PM
 */

public class InsertSort {

    public static void main(String[] args) {

        int[] arr = {4,9,6,2,5,1,3,7};

        for(int i = 1; i < arr.length; i++) {

            for(int j = i ; j > 0; j--) {

                //找到小的值
                if(arr[j] < arr[j-1]) {

                    //交换
                    SortUtils.SwapValue(arr, j, j-1);
                    SortUtils.print(arr);
                    System.out.println();
                }

            }

        }
        SortUtils.print(arr);

    }
}
