package com.example.test.algorithm.leeCode.link;
/**
 * https://leetcode-cn.com/problems/partition-list/
 * @Description 
 * @author leiel
 * @Date 2020/5/21 8:54 AM
 */

public class _分割链表 {

    //输入: head = 1->4->3->2->5->2, x = 3
    //输出: 1->2->2->4->3->5
    public ListNode partition(ListNode head, int x) {

        //小于x链表
        ListNode beforeHead = new ListNode(0);
        ListNode beforeTail = beforeHead;

        //大于x链表
        ListNode endHead = new ListNode(0);
        ListNode endTail = endHead;

        while(head != null) {

            if(head.val < x) {
                beforeTail = beforeTail.next = head;
            } else {
                endTail = endTail.next = head;
            }
            head = head.next;
        }
        endTail.next = null;

        beforeTail.next = endHead.next;

        return beforeHead.next;

    }

}
