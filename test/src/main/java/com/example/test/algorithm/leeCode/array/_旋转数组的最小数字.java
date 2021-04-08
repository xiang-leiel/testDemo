package com.example.test.algorithm.leeCode.array;
/**
 * https://leetcode-cn.com/problems/xuan-zhuan-shu-zu-de-zui-xiao-shu-zi-lcof/
 * @Description 
 * @author leiel
 * @Date 2020/7/22 10:13 AM
 */
public class _旋转数组的最小数字 {

    /**
     * 示例 1：
     *
     * 输入：[3,4,5,1,2]
     * 输出：1
     *
     * 示例 2：
     *
     * 输入：[2,2,2,0,1]
     * 输出：0
     *
     * 利用其特性处理
     *
     * @param numbers
     * @return
     */
    public int minArray(int[] numbers) {

        if(numbers.length == 0) {
            return 0;
        }

        for(int i = 1; i < numbers.length; i++) {

            if (numbers[i] < numbers[i-1]) {
                return numbers[i];
            }

        }

        return numbers[0];

    }

}
