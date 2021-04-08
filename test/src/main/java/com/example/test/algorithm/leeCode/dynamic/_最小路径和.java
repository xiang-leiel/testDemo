package com.example.test.algorithm.leeCode.dynamic;
/**
 * https://leetcode-cn.com/problems/minimum-path-sum/
 * @Description 
 * @author leiel
 * @Date 2020/6/12 5:20 PM
 */
public class _最小路径和 {

    /**
     * 输入:
     * [
     *   [1,3,1],
     *   [1,5,1],
     *   [4,2,1]
     * ]
     * 输出: 7
     * 解释: 因为路径 1→3→1→1→1 的总和最小。
     * @param grid
     * @return
     */
    public int minPathSum(int[][] grid) {

        int rows = grid.length;
        int cols = grid[0].length;

        int[][] dp = new int[rows][cols];

        dp[0][0] = grid[0][0];

        for(int row = 1; row < rows; row++) {
            dp[row][0] = dp[row-1][0] + grid[row][0];
        }

        for(int col = 1; col < cols; col++) {
            dp[0][col] = dp[0][col-1] + grid[0][col];
        }
        //获取第0列
        for(int row = 1; row < rows; row++) {

            for(int col = 1; col < cols; col++) {

                dp[row][col] = Math.min(dp[row-1][col], dp[row][col-1]) + grid[row][col];

            }
        }

        return dp[rows-1][cols-1];

    }

}
