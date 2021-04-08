package com.example.test.algorithm.leeCode.array;

import com.example.test.utils.SortUtils;

/**
 * https://leetcode-cn.com/problems/merge-sorted-array/
 * @Description 合并两个有序数组
 * @author leiel
 * @Date 2020/5/18 12:01 PM
 */

public class MergeSortArray {

    /**
     * 采用三指针的方式实现  从小到大排序，使用尾指针
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public static void merge(int[] nums1, int m, int[] nums2, int n) {

        //输入:
        //nums1 = [1,2,3,0,0,0], m = 3
        //nums2 = [2,5,6],       n = 3
        //
        //输出: [1,2,2,3,5,6]

        if(m == 0) {

            System.arraycopy(nums2,0,nums1,0,nums2.length);

        }else {
            int s1 = m-1;

            int s2 = n-1;

            int newPoint = nums1.length-1;

            while(s2 >= 0){

                if(s1 >= 0 && nums2[s2] < nums1[s1]) {
                    nums1[newPoint] = nums1[s1];
                    s1--;
                    newPoint--;
                }else {
                    //s1 < 0 || nums2[s2] < nums1[s1]
                    nums1[newPoint] = nums2[s2];
                    s2--;
                    newPoint--;

                }

            }
        }

        SortUtils.print(nums1);

    }

    public static void main(String[] args) {

        int[] nums1 = {2,0};
        int[] nums2 = {1};

        merge(nums1, 1, nums2, 1);
    }

}
