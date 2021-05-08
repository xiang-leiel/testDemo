package com.example.test.algorithm.leeCodeTwo._栈;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description 
 * @author leiel
 * @Date 2021/5/6 8:18 AM
 */

public class _路径总和II {

    List<List<Integer>> ret = new LinkedList<List<Integer>>();
    Deque<Integer> path = new LinkedList<Integer>();


    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {

        dfs(root, targetSum);
        return ret;

    }

    public void dfs(TreeNode root, int targetSum) {

        if (root == null) {
            return;
        }

        path.offerLast(root.val);
        targetSum -= root.val;
        if (root.left == null && root.right == null && targetSum == 0) {
            ret.add(new LinkedList<Integer>(path));
        }
        dfs(root.left, targetSum);
        dfs(root.right, targetSum);
        path.pollLast();

    }

    public void dfs2(TreeNode root, int targetSum, List<Integer> cur) {

        // 左右子树均为空，说明当前节点是一个叶子节点
        if (root.left == null && root.right == null) {
            // 满足条件。注意，将最后一个元素加入到集合中
            if (targetSum - root.val == 0) {
                cur.add(root.val);
                ret.add(new ArrayList<>(cur));
                cur.remove(cur.size() - 1);
            }
            return;
        }
        // 遍历左子树
        if (root.left != null) {
            // choose
            cur.add(root.val);
            // explore
            dfs2(root.left, targetSum - root.val, cur);
            // un choose
            cur.remove(cur.size() - 1);
        }
        // 遍历右子树
        if (root.right != null) {
            cur.add(root.val);
            dfs2(root.right, targetSum - root.val, cur);
            cur.remove(cur.size() - 1);
        }

    }

}
