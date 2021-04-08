package com.example.test.algorithm.leeCode.string;

import com.example.test.algorithm.leeCode.tree.TreeNode;

/**
 * https://leetcode-cn.com/problems/subtree-of-another-tree/
 * @Description 
 * @author leiel
 * @Date 2020/6/8 11:19 AM
 */

public class _另一个树的子树 {

    /**
     * 二叉树的序列化
     * 一般情况下都是序列化成一个字符串
     */
    public boolean isSubtree(TreeNode s, TreeNode t) {

        //将父树的序列化
        String sNew = postSerialize(s);
        //将子树的序列化
        String tNew = postSerialize(t);
        return sNew.contains(tNew);
    }

    /**
     * 利用后序遍历的方式进行序列化
     * @param root
     * @return
     */
    public static String postSerialize(TreeNode root) {

        StringBuilder stringBuilder = new StringBuilder();

        postSerialize(root, stringBuilder);

        return stringBuilder.toString();
    }

    public static void postSerialize(TreeNode root, StringBuilder sb){

        if(root.left == null) {
            sb.append("#!");
        } else {
            postSerialize(root.left, sb);
        }
        if(root.right == null) {
            sb.append("#!");
        } else {
            postSerialize(root.right, sb);
        }
        sb.append(root.val).append("!");

    }

    public static void main(String[] args) {

        //构建二叉树
        TreeNode treeNode = new TreeNode(3);
        treeNode.left = new TreeNode(4);
        treeNode.right = new TreeNode(5);

        treeNode.left.left = new TreeNode(1);
        treeNode.left.right = new TreeNode(2);

        System.out.println(postSerialize(treeNode));

    }
}
