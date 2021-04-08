package com.example.test.algorithm.leeCode.array;
/**
 * https://leetcode-cn.com/problems/3sum-closest/
 * @Description 
 * @author leiel
 * @Date 2020/6/24 7:28 PM
 */
public class _最接近的三数之和 {

    /**
     * 输入：nums = [-1,2,1,-4], target = 1
     * 输出：2
     * 解释：与 target 最接近的和是 2 (-1 + 2 + 1 = 2) 。
     * @param nums
     * @param target
     * @return
     */
    public static int threeSumClosest(int[] nums, int target) {

        int sum = nums[0] + nums[1] + nums[2];
        int max = sum - target > 0 ? sum - target : target - sum;;
        int result = sum;

        for(int curIndex = 0; curIndex < nums.length; curIndex++) {

            int firstIndex = curIndex + 1;
            int twoIndex = curIndex + 2;

            while(firstIndex < nums.length-1) {

                while (twoIndex <= nums.length-1) {

                    sum = sumValue(nums, curIndex, firstIndex, twoIndex);

                    int diff = sum - target;
                    if(diff < 0) {
                        diff = target - sum;
                    }
                    if(diff < max) {
                        max = diff;
                        result = sum;
                    }

                    twoIndex++;

                }
                twoIndex = firstIndex + 1;
                firstIndex++;
            }


        }

        return result;

    }

    //返回最大值
    private static int sumValue(int[] nums, int cur, int first, int two) {

        return nums[cur] + nums[first] + nums[two];

    }

    public static void main(String[] args) {
        int[] nums = {1,2,4,8,16,32,64,128};

        System.out.println(threeSumClosest(nums, 82));

    }

}
