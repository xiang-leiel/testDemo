package com.example.test.algorithm.sort;
/**
 * @Description 
 * @author leiel
 * @Date 2020/8/21 4:16 PM
 */

public class MergeSort {


    /**
     * 核心思想是先分 再合
     * @param args
     */
    public static void main(String[] args) {

        int[] arr = {4,9,6,2,5,1,3,7};
        sort(arr, 0, arr.length-1);

    }

    public static void merge(int[] a, int left, int mid, int right) {

        int[] tmp = new int[a.length];
        int i= left;
        int j = mid+1;
        int k=0;
        // 把较小的数先移到新数组中
        while(i<=mid && j<=right){
            if(a[i]<a[j]){
                tmp[k++] = a[i++];
            }else{
                tmp[k++] = a[j++];
            }
        }
        // 把左边剩余的数移入数组
        while(i<=mid){
            tmp[k++] = a[i++];
        }
        // 把右边边剩余的数移入数组
        while(j<=right){
            tmp[k++] = a[j++];
        }
        // 把新数组中的数覆盖nums数组
        for(int x=0;x<tmp.length;x++){
            a[x+left] = tmp[x];
        }
        System.out.println();
    }

    public static void sort(int[] arr, int leftIndex, int rightIndex) {

        if(leftIndex < rightIndex) {

            int mid = (leftIndex + rightIndex)/2;

            sort(arr, leftIndex, mid);
            sort(arr, mid+1, rightIndex);
            merge(arr, leftIndex, mid, rightIndex);

        }

    }

}
