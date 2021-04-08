package com.example.test.algorithm.leeCode.dynamic;
/**
 * https://leetcode-cn.com/problems/li-wu-de-zui-da-jie-zhi-lcof/
 * @Description 
 * @author leiel
 * @Date 2020/6/12 11:22 AM
 */

public class _礼物的最大价值 {

    /**
     * 输入:
     * [
     *   [1,3,1],
     *   [1,5,1],
     *   [4,2,1]
     * ]
     * 输出: 12
     * 解释: 路径 1→3→5→2→1 可以拿到最多价值的礼物
     * @param grid
     * @return
     */
    public int maxValue(int[][] grid) {

        //行
        int rows = grid.length;
        //列
        int cols = grid[0].length;

        int[][] dp = new int[rows][cols];

        dp[0][0] = grid[0][0];

        //获取第0行
        for(int col = 1; col < cols; col++) {
            dp[0][col] = dp[0][col-1] + grid[0][col];
        }

        //获取第0列
        for(int row = 1; row < rows; row++) {
            dp[row][0] = dp[row-1][0] + grid[row][0];
        }

        //获取第0列
        for(int row = 1; row < rows; row++) {

            for(int col = 1; col < cols; col++) {

                dp[row][col] = Math.max(dp[row-1][col], dp[row][col-1]) + grid[row][col];

            }
        }

        return dp[rows-1][cols-1];

    }

}
