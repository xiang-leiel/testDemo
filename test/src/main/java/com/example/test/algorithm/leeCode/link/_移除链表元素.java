package com.example.test.algorithm.leeCode.link;

/**
 *
 * https://leetcode-cn.com/problems/remove-linked-list-elements/
 * @Description 
 * @author leiel
 * @Date 2020/5/19 2:36 PM
 */

public class _移除链表元素 {


    /**
     * 输入: 1->2->6->3->4->5->6, val = 6
     * 输出: 1->2->3->4->5
     * @param head
     * @param val
     * @return
     */
    //思路一
    public ListNode removeElements(ListNode head, int val) {

        if(head == null) {
            return null;
        }

        //新链表头结点
        ListNode newHead = null;

        //新链表尾节点
        ListNode newTail = null;

        while(head != null) {


            if(head.val != val) {

                if(newTail == null) {

                    newHead = head;
                    newTail = head;

                } else {

                    newTail = newTail.next = head;

                }
            }

            head = head.next;
        }

        if(newTail != null) {
            newTail.next = null;
        }
        return newHead;

    }

    //思路二  虚拟头结点
    public ListNode removeElementsTwo(ListNode head, int val) {

        if(head == null) {
            return null;
        }

        //新链表头结点
        ListNode newHead = new ListNode(0);

        //新链表尾节点
        ListNode newTail = newHead;

        while(head != null) {

            if(head.val != val) {

                newTail = newTail.next = head;

            }

            head = head.next;

        }

        newTail.next = null;

        return newHead.next;

    }

}
