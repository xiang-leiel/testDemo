package com.example.test.algorithm.leeCode.array;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode-cn.com/problems/maximum-length-of-repeated-subarray/
 * @Description 
 * @author leiel
 * @Date 2020/7/1 9:16 AM
 */
public class _最长重复子数组 {

    /**
     * 输入:
     * A: [1,2,3,2,1]
     * B: [3,2,1,4,7]
     * 输出: 3
     * 解释:
     * 长度最长的公共子数组是 [3, 2, 1]。
     * @param A
     * @param B
     * @return
     */
    public static int findLength(int[] A, int[] B) {

        int max = 0;
        int[][] dp = new int[A.length + 1][B.length + 1];
        for (int i = 1; i <= A.length; i++) {
            for (int j = 1; j <= B.length; j++) {
                if (A[i - 1] == B[j - 1])
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                else
                    dp[i][j] = 0;
                max = Math.max(max, dp[i][j]);
            }
        }
        return max;

    }

    public static void main(String[] args) {

        int[] numsA = {1,1,0,0,1};
        int[] numsB = {1,1,0,0,0};

        System.out.println(findLength(numsA, numsB));

    }

}
