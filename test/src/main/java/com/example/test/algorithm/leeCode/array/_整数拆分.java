package com.example.test.algorithm.leeCode.array;
/**
 * https://leetcode-cn.com/problems/integer-break/
 * @Description 
 * @author leiel
 * @Date 2020/7/30 3:02 PM
 */

public class _整数拆分 {

    /**
     * 示例 1:
     *
     * 输入: 2
     * 输出: 1
     * 解释: 2 = 1 + 1, 1 × 1 = 1。
     * 示例 2:
     *
     * 输入: 10
     * 输出: 36
     * 解释: 10 = 3 + 3 + 4, 3 × 3 × 4 = 36。
     *
     * @param n
     * @return
     */
    public int integerBreak(int n) {

        int[] dp = new int[n + 1];
        for (int i = 2; i <= n; i++) {
            int curMax = 0;
            for (int j = 1; j < i; j++) {
                curMax = Math.max(curMax, Math.max(j * (i - j), j * dp[i - j]));
            }
            dp[i] = curMax;
        }
        return dp[n];

    }

}
