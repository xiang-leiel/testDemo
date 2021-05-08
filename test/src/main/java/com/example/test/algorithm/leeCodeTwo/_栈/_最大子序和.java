package com.example.test.algorithm.leeCodeTwo._栈;
/**
 * @Description 
 * @author leiel
 * @Date 2021/4/20 8:18 AM
 */

public class _最大子序和 {

    /**
     * 暴力解法  两次遍历
     * @param nums
     * @return
     */
    public static int maxSubArray(int[] nums) {

        if (nums.length == 1) {
            return nums[0];
        }

        int max = nums[0];


        for (int i = 0; i < nums.length; i++) {

            int sum = nums[i];

            if (sum > max) {
                max = sum;
            }

            for (int j = i+1; j < nums.length; j++) {

                sum = nums[j] + sum;

                if (sum > max) {
                    max = sum;
                }

            }



        }

        return max;

    }

    public static int maxSubArrayTwo(int[] nums) {

        if (nums.length == 1) {
            return nums[0];
        }

        int max = nums[0];

        //最大和
        int maxSum = 0;


        for (int i = 0; i < nums.length; i++) {

            if (maxSum < 0) {
                maxSum = nums[i];
            } else {
                maxSum += nums[i];
            }

            max = Math.max(maxSum, max);

        }

        return max;

    }

    public static void main(String[] args) {

        int[] nums = {-2,1,-3,4,-1,2,1,-5,4};

        System.out.println(maxSubArray(nums));

    }

}
