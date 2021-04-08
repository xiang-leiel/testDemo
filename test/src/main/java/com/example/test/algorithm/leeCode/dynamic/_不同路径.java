package com.example.test.algorithm.leeCode.dynamic;
/**
 * https://leetcode-cn.com/problems/unique-paths/
 * @Description 
 * @author leiel
 * @Date 2020/6/12 5:29 PM
 */
public class _不同路径 {

    /**
     * 输入: m = 3, n = 2
     * 输出: 3
     * 解释:
     * 从左上角开始，总共有 3 条路径可以到达右下角。
     * 1. 向右 -> 向右 -> 向下
     * 2. 向右 -> 向下 -> 向右
     * 3. 向下 -> 向右 -> 向右
     * @param m
     * @param n
     * @return
     */
    public int uniquePaths(int m, int n) {

        int[][] dp = new int[m][n];

        for(int row = 0; row < m; row++) {
            dp[row][0] = 1;
        }
        for(int col = 0; col < n; col++) {
            dp[0][col] = 1;
        }

        //获取第0列
        for(int row = 1; row < m; row++) {

            for(int col = 1; col < n; col++) {

                dp[row][col] = dp[row-1][col] + dp[row][col-1];

            }
        }

        return dp[m-1][n-1];

    }
}
