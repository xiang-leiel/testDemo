package com.example.test.algorithm.leeCode.array;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/kth-smallest-element-in-a-sorted-matrix/
 * @Description 
 * @author leiel
 * @Date 2020/7/2 3:37 PM
 */

public class _有序矩阵中第K小的元素 {

    /**
     * matrix = [
     *    [ 1,  5,  9],
     *    [10, 11, 13],
     *    [12, 13, 15]
     * ],
     * k = 8,
     * 返回 13。
     * @param matrix
     * @param k
     * @return
     */
    public static int kthSmallest(int[][] matrix, int k) {

        int rows = matrix.length;

        int cols = matrix[0].length;

        int[] snap = new int[rows*cols];

        int x = 0;

        for(int i = 0; i < rows; i++) {

            for (int j = 0; j < cols; j++) {

                snap[x++] = matrix[i][j];

            }

        }

        Arrays.sort(snap);

        return snap[k-1];

    }

    public static void main(String[] args) {

        int[][] matrix = new int[3][3];

        matrix[0][0] = 1;
        matrix[0][1] = 5;
        matrix[0][2] = 9;
        matrix[1][0] = 10;
        matrix[1][1] = 11;
        matrix[1][2] = 13;
        matrix[2][0] = 12;
        matrix[2][1] = 13;
        matrix[2][2] = 15;


        System.out.println((kthSmallest(matrix, 8)));

    }

}
