package com.example.test.algorithm.leeCode.link;
/**
 * @Description 
 * @author leiel
 * @Date 2020/5/19 2:49 PM
 */

public class ListNode {

    int val;

    ListNode next;

    ListNode(int x) { val = x; }

    @Override
    public String toString() {

        return val + " -> " + next;
    }
}
