package com.example.test.algorithm.leeCodeTwo._栈;
/**
 * @Description 
 * @author leiel
 * @Date 2021/4/21 8:38 AM
 */

public class _搜索旋转排序数组 {

    public int search(int[] nums, int target) {


        for (int i = 0; i < nums.length; i++) {

            if (nums[i] == target) {
                return i;
            }

        }
        return -1;

    }


    /**
     * 找到两部分有序数组  再用二分干
     * @param nums
     * @param target
     * @return
     */
    public int searchTwo(int[] nums, int target) {


        int l = 0;
        int r = nums.length - 1;

        while (l <= r) {
            //获取中间
            int mid = (l+r)/2;

            if (nums[mid] == target) {
                return mid;
            }

            //右边有序
            if(nums[mid] < nums[r]) {

                //目标值在右边
                if (target > nums[mid] && target <= nums[r] ){
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }


            } else {
                //左边有序
                //目标值在左边
                if (target < nums[mid] && target >= nums[l] ){
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }

            }

        }

        return -1;

    }

}
