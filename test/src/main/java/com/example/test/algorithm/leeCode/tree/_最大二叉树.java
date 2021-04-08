package com.example.test.algorithm.leeCode.tree;

import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/maximum-binary-tree/
 * @Description 
 * @author leiel
 * @Date 2020/6/2 8:50 AM
 */

public class _最大二叉树 {

    /**
     * 输入：[3,2,1,6,0,5]
     * 输出：返回下面这棵树的根节点：
     *
     *       6
     *     /   \
     *    3     5
     *     \    /
     *      2  0
     *        \
     *         1
     *
     * @param nums
     * @return
     */
    public TreeNode constructMaximumBinaryTree(int[] nums) {

        if(nums == null) {
            return null;
        }

        TreeNode treeNode = getRoot(nums, 0, nums.length);

        return treeNode;

    }

    public TreeNode getRoot(int[] nums, int left, int right) {

        if(left == right) {
            return null;
        }

        int max = left;
        for(int i = left+1; i < right; i++) {
            if(nums[i] > nums[max]) max = i;
        }

        TreeNode root = new TreeNode(nums[max]);
        root.left = getRoot(nums, left, max);
        root.right = getRoot(nums, max+1, right);

        return root;

    }

    /**
     * 输入：[3,2,1,6,0,5]
     *
     * 获取二叉树节点父节点的位置索引  //利用栈实现左右两边第一个最大值 - - 确保栈的元素是从下往上依次递减
     *                            //若获取左右两边第一个最小值 - - 则需确保栈的元素是从下往上依次递增
     * @param nums
     * @return
     */
    public static int[] parentIndexes(int[] nums) {

        int[] result = new int[nums.length];

        //获取左边第一个最大的索引数组
        int[] lis = new int[nums.length];
        //获取右边第一个最大的索引数组
        int[] ris = new int[nums.length];

        for(int i = 0; i < nums.length; i++) {
            lis[i] = -1;
            ris[i] = -1;
            result[i] = -1;
        }
        //遍历数组，比较获取最小的值的索引，即为父节点的索引

        //初始化栈 存放数组索引
        Stack<Integer> stack = new Stack<>();

        for(int i = 0; i < nums.length; i++) {

            if(stack.empty()) {
                stack.push(i);
            }else {
                //判断栈顶数据是否大于新入栈数据，若大于则入栈，否则弹出栈
                while(!stack.empty() && nums[stack.peek()] < nums[i]) {
                    //此时为i位置数据右边最大的
                    ris[stack.peek()] = i;
                    stack.pop();
                }
                if(stack.empty()) {
                    stack.push(i);
                }
                if(!stack.empty() && nums[stack.peek()] > nums[i]) {
                    lis[i] = stack.peek();
                    stack.push(i);
                }

            }

        }

        //比较两个数组 取值小的索引
        for(int i = 0; i < nums.length; i++) {

            if(lis[i] == -1 && ris[i] == -1) {
                continue;
            }
            if(lis[i] == -1) {
                result[i] = ris[i];
                continue;
            }
            if(ris[i] == -1) {
                result[i] = lis[i];
                continue;
            }

            if(nums[lis[i]] > nums[ris[i]]) {
                result[i] = ris[i];
            }else {
                result[i] = lis[i];
            }
        }

        return result;

    }

    public static void main(String[] args) {
        int[] nums = {3,2,1,6,0,5};

        parentIndexes(nums);
    }

}
