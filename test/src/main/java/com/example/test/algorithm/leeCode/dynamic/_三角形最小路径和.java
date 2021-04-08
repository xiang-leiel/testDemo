package com.example.test.algorithm.leeCode.dynamic;

import java.util.List;

/**
 * https://leetcode-cn.com/problems/triangle/
 * @Description 
 * @author leiel
 * @Date 2020/7/14 11:11 AM
 */

public class _三角形最小路径和 {

    /**
     * 给定三角形：
     *
     * [
     *      [2],
     *     [3,4],
     *    [6,5,7],
     *   [4,1,8,3]
     * ]
     * 自顶向下的最小路径和为 11（即，2 + 3 + 5 + 1 = 11）。
     * @param triangle
     * @return
     */
    public int minimumTotal(List<List<Integer>> triangle) {

        int n = triangle.size();
        // dp[i][j] 表示从点 (i, j) 到底边的最小路径和。
        int[][] dp = new int[n + 1][n + 1];
        // 从三角形的最后一行开始递推。
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                dp[i][j] = Math.min(dp[i + 1][j], dp[i + 1][j + 1]) + triangle.get(i).get(j);
            }
        }
        return dp[0][0];

    }

}
