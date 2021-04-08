package com.example.test.algorithm.leeCode.array;
/**
 * https://leetcode-cn.com/problems/search-insert-position/
 * @Description 
 * @author leiel
 * @Date 2020/7/17 9:55 PM
 */

public class _搜索插入位置 {

    /**
     * 输入: [1,3,5,6], 5
     * 输出: 2
     * @param nums
     * @param target
     * @return
     */
    public static int searchInsert(int[] nums, int target) {

        int n = nums.length;
        int left = 0, right = n - 1, ans = n;
        while (left <= right) {
            int mid = (right  + left)/2;
            if (target <= nums[mid]) {
                ans = mid;
                right = mid - 1;
            } else {

                left = mid + 1;
            }
        }
        return ans;

    }


    public static void main(String[] args) {

        int[] nums = {1,3};

        System.out.println(nums.length / 2);

        System.out.println(searchInsert(nums, 1));

    }

}
