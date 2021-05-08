package com.example.test.algorithm.leeCodeTwo._栈;
/**
 * @Description 
 * @author leiel
 * @Date 2021/4/29 8:09 AM
 */

public class _验证二叉搜索树 {

    public boolean isValidBST(TreeNode root) {

        return dfs(root, Long.MIN_VALUE, Long.MAX_VALUE);

    }

    private boolean dfs(TreeNode root, long min, long max) {

        if (root == null) {
            return true;
        }

        if (max <= root.val || min >= root.val) {
            return false;
        }

        return dfs(root.left, min, root.val) && dfs(root.right, root.val, max);

    }

    public static void main(String[] args) {
        System.out.println(Long.MIN_VALUE);
        System.out.println(Long.MAX_VALUE);
        System.out.println(Integer.MIN_VALUE);
        System.out.println(Integer.MAX_VALUE);
    }

}
