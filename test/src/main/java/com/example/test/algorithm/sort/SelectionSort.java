package com.example.test.algorithm.sort;

import com.example.test.utils.SortUtils;

/**
 * @Description   基础选择排序
 * @author leiel
 * @Date 2020/4/8 12:15 PM
 */
public class SelectionSort {

    public static void main(String[] args) {

        int[] arr = {4,9,6,2,5,1,3,7};

        //排序
        for(int j = 0 ; j < arr.length-1; j++) {

            //找出最小值
            int minPos = j;

            for(int i = j+1; i < arr.length; i++) {
                if(arr[i] < arr[minPos]) {
                    minPos = i;
                }
            }

            //最小值交换
            SortUtils.SwapValue(arr, minPos, j);

            //打印
            SortUtils.print(arr);
            System.out.println();

        }

        //打印
        SortUtils.print(arr);
    }

}
