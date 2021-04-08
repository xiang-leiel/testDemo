package com.example.test.algorithm.leeCode.link;
/**
 * https://leetcode-cn.com/problems/add-two-numbers/
 * @Description 
 * @author leiel
 * @Date 2020/5/19 4:07 PM
 */

public class _两数相加 {


    /**
     *
     * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
     * 输出：7 -> 0 -> 8
     * 原因：342 + 465 = 807
     *
     * 添加虚拟节点 初始化头结点和尾结点
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        if(l1 == null) {
            return l2;
        }

        if(l2 == null) {
            return l1;
        }

        ListNode headNode = new ListNode(0);

        ListNode tailNode = headNode;

        //进位值
        int carry = 0;
        while(l1 != null || l2 != null) {

            int sum = 0;
            if(l1 == null) {
                sum = 0 + l2.val + carry;
            }else if(l2 == null) {
                sum = l1.val + 0 + carry;
            }else{
                sum = l1.val + l2.val + carry;
            }

            carry = sum / 10;

            tailNode = tailNode.next = new ListNode(sum % 10);

            if(l1 != null) {
                l1 = l1.next;
            }
            if(l2 != null) {
                l2 = l2.next;
            }

        }

        if (carry != 0) {
            tailNode = tailNode.next = new ListNode(carry);
        }

        return headNode.next;

    }


}
