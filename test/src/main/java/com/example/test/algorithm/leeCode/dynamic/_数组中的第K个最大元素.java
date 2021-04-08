package com.example.test.algorithm.leeCode.dynamic;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * https://leetcode-cn.com/problems/kth-largest-element-in-an-array/
 * @Description 
 * @author leiel
 * @Date 2020/6/29 1:47 PM
 */

public class _数组中的第K个最大元素 {

    /**
     * 输入: [3,2,1,5,6,4] 和 k = 2
     * 输出: 5
     * @param nums
     * @param k
     * @return
     */
    public static int findKthLargest(int[] nums, int k) {

        Arrays.sort(nums);

        return nums[nums.length - k];

    }

    public static void main(String[] args) {

        int[] nums = {3,2,1,5,6,4};

        System.out.println(findKthLargest(nums, 2));

    }

}
