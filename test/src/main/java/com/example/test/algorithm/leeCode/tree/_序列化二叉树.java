package com.example.test.algorithm.leeCode.tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode-cn.com/problems/xu-lie-hua-er-cha-shu-lcof/
 * @Description 
 * @author leiel
 * @Date 2020/6/16 3:28 PM
 */

public class _序列化二叉树 {

    /**
     *     1
     *    / \
     *   2   3
     *      / \
     *     4   5
     *
     * 序列化为 "[1,2,3,null,null,4,5]"
     * @param root
     * @return
     */
    public static String serialize(TreeNode root) {

        if(root == null) {
            return "[]";
        }

        //层序遍历
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        String result = "[" + root.val;

        while(!queue.isEmpty()){

            TreeNode t1 = queue.poll();

            if (t1.left != null) {
                queue.add(t1.left);
                result += "," + t1.left.val;
            }else {
                result += ",null";
            }

            if (t1.right != null) {
                queue.add(t1.right);
                result += "," + t1.right.val;
            }else{
                result += ",null";
            }

        }
        // 处理完成之后添加上结束符
        result += "]";

        return result;

    }

    /**
     *  Decodes your encoded data to tree.
     *  [1,2,3,null,null,4,5]
     */
    public TreeNode deserialize(String data) {

        //处理特殊情况
        if(data.equals("[]")) {
            return null;
        }
        //将字符串转为数组
        String[] vals = data.substring(1,data.length()-1).split(",");

        //初始化树
        TreeNode root = new TreeNode(Integer.parseInt(vals[0]));

        //构造队列
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        int i = 1;
        while(!queue.isEmpty()){

            TreeNode node = queue.poll();

            if(!vals[i].equals("null")){
                node.left = new TreeNode(Integer.parseInt(vals[i]));
                queue.add(node.left);
            }
            i++;
            if(!vals[i].equals("null")){
                node.right=new TreeNode(Integer.parseInt(vals[i]));
                queue.add(node.right);
            }
            i++;
        }
        return root;

    }

    public static void main(String[] args) {

        // 手动创建Leetcode题页上的测试用例。
        // 当然, 有更好的更智能的创建二叉树的方式, 有兴趣的同学可以自行研究编写程序:)

        /*****************
         * 测试用例:
         *
         *       10
         *      /  \
         *     5   -3
         *    / \    \
         *   3   2   11
         *  / \   \
         * 3  -2   1
         *****************/
        TreeNode node1 = new TreeNode(3);
        TreeNode node2 = new TreeNode(-2);

        TreeNode node3 = new TreeNode(3);
        node3.left = node1;
        node3.right = node2;

        TreeNode node4 = new TreeNode(1);
        TreeNode node5 = new TreeNode(2);
        node5.right = node4;

        TreeNode node6 = new TreeNode(5);
        node6.left = node3;
        node6.right = node5;

        TreeNode node7 = new TreeNode(11);
        TreeNode node8 = new TreeNode(-3);
        node8.right = node7;

        TreeNode node9 = new TreeNode(10);
        node9.left = node6;
        node9.right = node8;

        System.out.println(serialize(node9));
    }

}
