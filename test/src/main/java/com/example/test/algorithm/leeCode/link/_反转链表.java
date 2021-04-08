package com.example.test.algorithm.leeCode.link;

import org.springframework.stereotype.Service;

/**
 * https://leetcode-cn.com/problems/reverse-linked-list/
 * @Description 
 * @author leiel
 * @Date 2020/5/21 10:04 PM
 */
public class _反转链表 {

    /**
     * 输入: 1->2->3->4->5->NULL
     * 输出: 5->4->3->2->1->NULL
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {

        if(head == null) {
            return null;
        }

        ListNode prev = null;
        ListNode curr = head;

        while (curr != null) {

            //不破坏原有链表结构
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;

            curr = next;

        }

        return prev;
    }

}
