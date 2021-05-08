package com.example.test.algorithm.leeCodeTwo._栈;
/**
 * @Description 
 * @author leiel
 * @Date 2021/4/23 8:23 AM
 */

public class _爬楼梯 {

    /**
     * f(x)=f(x-1)+f(x-2)
     * @param n
     * @return
     */
    public static int climbStairs(int n) {

        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }

        return climbStairs(n-1)+climbStairs(n-2);

    }

    public static int climbStairsV2(int n) {

        int[] dp = new int[n+1];

        dp[0] = 1;

        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i-1]+dp[i-2];
        }
        return dp[n];

    }

    public static void main(String[] args) {

        System.out.println(climbStairsV2(5));

    }

}
