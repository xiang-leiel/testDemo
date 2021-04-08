package com.example.test.algorithm.leeCode.link;
/**
 * https://leetcode-cn.com/problems/palindrome-linked-list/
 * @Description 
 * @author leiel
 * @Date 2020/5/21 10:02 PM
 */

public class _回文链表 {

    public static boolean isPalindrome(ListNode head) {

        if(head == null || head.next == null) {
            return true;
        }
        if(head.next.next == null) {
            return head.val == head.next.val;
        }

        //找到中间节点
        ListNode mid = getMid(head);

        //反转中间节点后的链表
        ListNode reverseNode = reverseList(mid.next);

        ListNode newHead = head;

        ListNode rOldHead = reverseNode;

        //循环遍历
        Boolean result = true;
        while(reverseNode != null) {

            if(newHead.val != reverseNode.val) {
                result = false;
                break;
            }

            newHead = newHead.next;

            reverseNode = reverseNode.next;

        }

        //翻转有半部分
        reverseList(rOldHead);

        return result;

    }

    public static ListNode reverseList(ListNode head) {

        if(head == null) {
            return null;
        }

        ListNode newHead = null;

        while (head != null) {

            //临时节点
            ListNode tmp = head.next;

            //翻转
            head.next = newHead;
            newHead = head;

            head = tmp;

        }

        return newHead;
    }

    public static ListNode getMid(ListNode head) {

        ListNode fastNode = head;
        ListNode slowNode = head;

        while(fastNode != null && fastNode.next != null && fastNode.next.next != null) {

            fastNode = fastNode.next.next;

            slowNode = slowNode.next;

        }

        return slowNode;

    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);

        head.next = new ListNode(1);

        head.next.next = new ListNode(2);

        head.next.next.next = new ListNode(1);

        System.out.println(head);

        System.out.println(isPalindrome(head));

        System.out.println(head);
    }

}
