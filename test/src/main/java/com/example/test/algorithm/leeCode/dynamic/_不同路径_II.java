package com.example.test.algorithm.leeCode.dynamic;
/**
 * https://leetcode-cn.com/problems/unique-paths-ii/
 * @Description 
 * @author leiel
 * @Date 2020/7/6 8:32 AM
 */

public class _不同路径_II {

    public static int uniquePathsWithObstacles(int[][] obstacleGrid) {

        int row = obstacleGrid.length;

        int cols = obstacleGrid[0].length;

        int[][] dp = new int[row][cols];

        //初始化
        boolean rowFlag = false;
        for(int i = 0; i < row; i++){

            if(obstacleGrid[i][0] == 1) {
                rowFlag = true;
            }else if(!rowFlag){
                dp[i][0] = 1;
            }

        }

        boolean colsFlag = false;
        for(int i = 0; i < cols; i++){

            if(obstacleGrid[0][i] == 1) {
                colsFlag = true;
            }else if(!colsFlag){
                dp[0][i] = 1;
            }

        }

        for(int i = 1; i < row; i++) {

            for(int j = 1; j < cols; j++) {

                if(obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0;
                }else {
                    dp[i][j] = dp[i-1][j] + dp[i][j-1];
                }

            }

        }


        return dp[row-1][cols-1];
    }


    public static void main(String[] args) {

        int[][] nums = new int[1][2];

        nums[0][0] = 1;
        nums[0][1] = 2;

        System.out.println(uniquePathsWithObstacles(nums));

    }

}
