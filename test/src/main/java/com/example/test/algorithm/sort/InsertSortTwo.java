package com.example.test.algorithm.sort;

import com.example.test.utils.SortUtils;

/**
 * @Description 插入排序 不使用交换
 * @author leiel
 * @Date 2020/4/13 9:02 AM
 */

public class InsertSortTwo {

    public static void main(String[] args) {

        int[] arr = {4,9,6,2,5,1,3,7};

        for(int i = 1; i < arr.length; i++) {

            int temp = arr[i];
            int count = 0;

            for(int j = i ; j > 0; j--) {

                if(temp < arr[j-1]) {

                    arr[j] = arr[j-1];
                    count++;
                }
                SortUtils.print(arr);
                System.out.println();

            }
            arr[i-count] = temp;

        }
        SortUtils.print(arr);

    }

}
