package com.example.test.algorithm.leeCode.link;


/**
 * @Description
 * @author leiel
 * @Date 2020/5/22 8:37 AM
 */
public class LinkUtils {

    /**
     * 获取链表的中间节点
     * @param head
     * @return
     */
    public static ListNode getMid(ListNode head) {

        ListNode fastNode = head;
        ListNode slowNode = head;

        while(fastNode != null) {

            fastNode = fastNode.next.next;

            slowNode = slowNode.next;

        }

        return slowNode;

    }

}
