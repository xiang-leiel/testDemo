package com.example.test.algorithm.leeCode.tree;
/**
 * https://leetcode-cn.com/problems/convert-sorted-array-to-binary-search-tree/
 * @Description 
 * @author leiel
 * @Date 2020/7/3 2:01 PM
 */
public class _将有序数组转换为二叉搜索树 {

    /**
     * 给定有序数组: [-10,-3,0,5,9],
     *
     * 一个可能的答案是：[0,-3,9,-10,null,5]，它可以表示下面这个高度平衡二叉搜索树：
     *
     *       0
     *      / \
     *    -3   9
     *    /   /
     *  -10  5
     *
     * @param nums
     * @return
     */
    public static TreeNode sortedArrayToBST(int[] nums) {

        return helper(nums, 0, nums.length - 1);

    }

    public static TreeNode helper(int[] nums, int left, int right) {

        if (left > right) {
            return null;
        }

        // 总是选择中间位置左边的数字作为根节点
        int mid = (left + right) / 2;

        TreeNode root = new TreeNode(nums[mid]);

        //左必右开
        root.left = helper(nums, left, mid - 1);
        root.right = helper(nums, mid + 1, right);

        return root;
    }

    public static void main(String[] args) {

        int[] nums = {0,1,2,3,4,5};

        sortedArrayToBST(nums);

    }

}
