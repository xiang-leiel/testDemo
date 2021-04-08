package com.example.test.algorithm.leeCode.array;
/**
 * https://leetcode-cn.com/problems/best-sightseeing-pair/
 * @Description 
 * @author leiel
 * @Date 2020/6/17 1:56 PM
 */

public class _最佳观光组合 {

    /**
     * 暴力解法 ---时间复杂度过大leetcode直接过不了
     *       0 1 2 3 4
     * 输入：[8,1,5,2,6]
     * 输出：11
     * 解释：i = 0, j = 2, A[i] + A[j] + i - j = 8 + 5 + 0 - 2 = 11
     * @param A
     * @return
     */
    public int maxScoreSightseeingPair(int[] A) {

        if(A == null) {
            return 0;
        }
        int max = A[0];
        for (int i = 0; i < A.length; i++) {
           for(int j = i+1; j < A.length; j++) {
               max = Math.max((A[i] + A[j] + i - j), max);
           }
        }
        return max;
    }

    /**
     * A[i] + A[j] + i - j  可以拆分为A[i]+i + A[j]-j
     * 时间复杂度降为O(n)
     * @param A
     * @return
     */
    public int maxScoreSightseeingPairTwo(int[] A) {

        if(A == null) {
            return 0;
        }
        int max = A[0] + 0;
        int ans = 0;
        for (int i = 0; i < A.length; i++) {

            ans = Math.max(ans, max + A[i] - i);

            max = Math.max(max, A[i] + i);

        }
        return max;
    }

    /**
     * 拆成求一个数组中两个数的最大和
     * 时间复杂度降为O(n)
     * @param A
     * @return
     */
    public static int maxScoreSightseeingPairThree(int[] A) {

        //重新定义数组 A[i]-[i]
        int[] diff = new int[A.length];
        for(int i = 0; i < A.length; i++) {
            diff[i] = A[i] - i;
        }

        int max = diff[0];
        int nextMax = diff[1];
        for (int i = 2; i < diff.length; i++) {

            if(diff[i] > max) {
                max = diff[i];
            }else if(diff[i] < max && diff[i] > nextMax) {
                nextMax =  diff[i];
            }

        }
        return max + nextMax;
    }

    public static void main(String[] args) {

        int[] num = {8,1,5,2,6};

        System.out.println(maxScoreSightseeingPairThree(num));

    }

}
