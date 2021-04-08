package com.example.test.algorithm.leeCode.stack_queue;

import java.util.Stack;

/**
 *
 * https://leetcode-cn.com/problems/min-stack/
 * @Description 
 * @author leiel
 * @Date 2020/5/25 9:35 AM
 */

public class MinStack {

/*    //存放正常数据
    private Stack<Integer> stack;

    //存放最小数据
    private Stack<Integer> MinStack;

    // initialize your data structure here.
    public MinStack() {
        stack = new Stack<>();
        MinStack = new Stack<>();
    }

    public void push(int x) {

        stack.push(x);
        if(MinStack.empty()) {
            MinStack.push(x);
        }else {
            MinStack.push(Math.min(x, MinStack.peek()));
        }

    }

    public void pop() {
        stack.pop();
        MinStack.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return MinStack.peek();
    }*/

    private ListNode head;

    /** initialize your data structure here. */
    public MinStack() {
        head = new ListNode(0,Integer.MAX_VALUE,null);

    }

    public void push(int x) {

        head = new ListNode(x, Math.min(x,head.min) , head);

    }

    public void pop() {

        head = head.next;

    }

    public int top() {

        return head.val;

    }

    public int getMin() {

        return head.min;

    }

    private static class ListNode {

        private int val;

        private int min;

        private ListNode next;

        public ListNode(int val, int min, ListNode next) {
            this.val = val;
            this.min = min;
            this.next = next;
        }
    }

}
