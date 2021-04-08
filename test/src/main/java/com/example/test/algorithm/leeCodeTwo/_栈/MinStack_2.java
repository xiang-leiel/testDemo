package com.example.test.algorithm.leeCodeTwo._栈;
/**
 * @Description 
 * @author leiel
 * @Date 2021/4/7 8:58 AM
 */

public class MinStack_2 {

    private Node head;
    private int min;

    /** initialize your data structure here. */
    public MinStack_2() {
        min = Integer.MAX_VALUE;
    }

    public void push(int x) {
        head = new Node(x, head, min);
        min = Math.min(x, min);
    }

    public void pop() {
        if (head == null) {
            throw new RuntimeException("Head Node Is Null");
        }
        if (min == head.val) {
            min = head.min;
        }
        head = head.next;
    }

    public int top() {
        if (head == null) {
            throw new RuntimeException("Head Node Is Null");
        }
        return head.val;
    }

    public int min() {
        return min;
    }

    public static class Node{
        int val;
        Node next;
        int min;
        public Node(int val, Node next, int min) {
            this.val = val;
            this.next = next;
            this.min = min;
        }
        public Node(int val, int min) {
            this(val, null, min);
        }
    }

    public static void main(String[] args) {
        MinStack_2 minStack_2 = new MinStack_2();
        minStack_2.push(2);
        minStack_2.push(3);
        minStack_2.push(0);
        minStack_2.pop();
    }

}
