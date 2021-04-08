package com.example.test.algorithm.sort;

import com.example.test.utils.SortUtils;

/**
 * @Description  优化版选择排序  每一次循环找到最大值和最小值
 * @author leiel
 * @Date 2020/4/8 3:37 PM
 */
public class SelectionSortTwo {

    public static void main(String[] args) {

        int[] arr = {4,9,6,2,5,1,3,7};

        //排序
        for(int j = 0 ; j < arr.length/2; j++) {

            //找出最小值
            int minPos = j;
            //找出最大值
            int maxPos = arr.length - j - 1;

            if(arr[minPos] > arr[maxPos]) {
                maxPos = minPos;
            }

            for(int i = j+1; i < arr.length - j; i++) {
                if(arr[i] < arr[minPos]) {
                    minPos = i;
                }
                if(arr[i] > arr[maxPos]) {
                    maxPos = i;
                }
            }

            //最小值交换
            SortUtils.SwapValue(arr, minPos, j);

            if(maxPos == j) {
                maxPos = minPos;
            }

            //最大值交换
            SortUtils.SwapValue(arr, maxPos, arr.length - j - 1);

            //打印
            SortUtils.print(arr);
            System.out.println();

        }

        //打印
        SortUtils.print(arr);
    }

}
