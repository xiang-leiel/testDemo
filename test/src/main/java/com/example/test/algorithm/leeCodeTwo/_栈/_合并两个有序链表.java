package com.example.test.algorithm.leeCodeTwo._栈;


/**
 * @Description 
 * @author leiel
 * @Date 2021/4/22 8:27 AM
 */

public class _合并两个有序链表 {

    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {

        if (l1 == null) {
            return l2;
        }

        if (l2 == null) {
            return l1;
        }

        ListNode prehead = new ListNode(-1);

        ListNode prev = prehead;

        while (l1 != null && l2 != null) {

            if (l1.val < l2.val) {
                prev.next = l1;
                l1 = l1.next;
            } else {
                prev.next = l2;
                l2 = l2.next;
            }
            prev = prev.next;
        }

        if (l2 != null) {
            prev.next = l2;
        }

        if (l1 != null) {
            prev.next = l1;
        }

        //移除头部null

        return prehead.next;

    }


    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public static void main(String[] args) {

        ListNode listNode = new ListNode();
        listNode.next = new ListNode(1);
        listNode.next.next =  new ListNode(2);
        listNode.next.next.next =  new ListNode(4);

        ListNode listNode2 = new ListNode();
        listNode2.next = new ListNode(1);
        listNode2.next.next =  new ListNode(3);
        listNode2.next.next.next =  new ListNode(4);

        mergeTwoLists(listNode, listNode2);



    }

}
