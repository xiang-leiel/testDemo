package com.example.test.algorithm.sort;

import com.example.test.utils.SortUtils;

/**
 * @Description 冒泡排序
 * @author leiel
 * @Date 2020/4/9 3:06 PM
 */

public class BubbleSort {

    public static void main(String[] args) {

        int[] arr = {4,9,6,2,5,1,3,7};

        for(int j = 0; j < arr.length-1; j++) {

            for(int i = 0; i < arr.length-1-j; i++) {

                if(arr[i] > arr[i+1]) {
                    SortUtils.SwapValue(arr, i, i+1);

                }
                //排序结果打印
                System.out.println();
                SortUtils.print(arr);
            }

        }

        //排序结果打印
        SortUtils.print(arr);

    }
}
