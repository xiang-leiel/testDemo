package com.example.test.algorithm.sort;
/**
 * @Description 快速排序  以基准值为参考点，
 * @author leiel
 * @Date 2020/8/21 3:59 PM
 */

public class QuickSort {

    public static void main(String[] args) {

        int[] arr = {4,9,6,2,5,1,3,7};
        sort(arr, 0, arr.length-1);

    }

    public static void sort(int[] arr, int leftIndex, int rightIndex) {

        if (leftIndex >= rightIndex) {
            return;
        }

        int left = leftIndex;
        int right = rightIndex;
        //待排序的第一个元素作为基准值
        int key = arr[left];

        //从左右两边交替扫描，直到left = right
        while (left < right) {
            while (right > left && arr[right] >= key) {
                //从右往左扫描，找到第一个比基准值小的元素
                right--;
            }

            //找到这种元素将arr[right]放入arr[left]中
            arr[left] = arr[right];

            while (left < right && arr[left] <= key) {
                //从左往右扫描，找到第一个比基准值大的元素
                left++;
            }

            //找到这种元素将arr[left]放入arr[right]中
            arr[right] = arr[left];
        }
        //基准值归位
        arr[left] = key;
        //对基准值左边的元素进行递归排序
        sort(arr, leftIndex, left - 1);
        //对基准值右边的元素进行递归排序。
        sort(arr, right + 1, rightIndex);

    }

}
