package com.example.test.algorithm.leeCode.string;

import java.util.Random;

/**
 * https://leetcode-cn.com/problems/divisor-game/
 * @Description 
 * @author leiel
 * @Date 2020/7/24 8:10 AM
 */

public class _除数博弈 {

    /**
     * 示例 1：
     *
     * 输入：2
     * 输出：true
     * 解释：爱丽丝选择 1，鲍勃无法进行操作。
     * 示例 2：
     *
     * 输入：3
     * 输出：false
     * 解释：爱丽丝选择 1，鲍勃也选择 1，然后爱丽丝无法进行操作。
     * @param N
     * @return
     */
    public static boolean divisorGame(int N) {

        return N % 2 == 0;
    }

    public static void main(String[] args) {
        System.out.println(divisorGame(3));
    }

}
