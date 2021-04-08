package com.example.test.algorithm.leeCode.link;

/**
 * https://leetcode-cn.com/problems/intersection-of-two-linked-lists/
 * @Description 
 * @author leiel
 * @Date 2020/5/20 9:09 AM
 */
public class _相交链表 {

    /**
     *
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {

        if(headA == null || headB == null) {
            return null;
        }

        ListNode currA = headA;

        ListNode currB = headB;

        while (currA != currB) {

            currA = currA == null ? headB : currA.next;

            currB = currB == null ? headA : currB.next;


        }

        return currA;

    }

}
