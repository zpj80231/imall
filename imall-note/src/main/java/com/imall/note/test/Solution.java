package com.imall.note.test;

class Solution {
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public static void main(String args[]){
        ListNode l1 = new ListNode(2);
        ListNode l11 = new ListNode(4);
        ListNode l111 = new ListNode(3);
        l1.next = l11;
        l11.next = l111;
        ListNode l2 = new ListNode(5);
        ListNode l22 = new ListNode(6);
        ListNode l222 = new ListNode(4);
        l2.next = l22;
        l22.next = l222;
        ListNode listNode = addTwoNumbers(l1, l2);
        while (listNode.next != null) {
            System.out.println(listNode.next.val);
        }
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        /*
          分别计算l1、l2的值
        */
        StringBuffer l1Str = new StringBuffer(String.valueOf(l1.val));
        StringBuffer l2Str = new StringBuffer(String.valueOf(l2.val));
        while(l1.next != null) {
            l1 = l1.next;
            l1Str.append(l1.val);
        }
        l1Str.reverse();
        while(l2.next != null) {
            l2 = l2.next;
            l2Str.append(l2.val);
        }
        l2Str.reverse();
    //             String a1="";
    // String a2="";
    // a1=l1.val+a1;
    // a2=l2.val+a2;
    // while (l1.next!=null)
    // {
    //     l1=l1.next;
    //     a1=l1.val+a1;
    // }
    // while (l2.next!=null)
    // {
    //     l2=l2.next;
    //     a2=l2.val+a2;
    // }

        /*
          将l1、l2的值相加并将结果转为字符串
        */
        Integer resultInt = Integer.parseInt(l1Str.toString()) + Integer.parseInt(l2Str.toString());
        String resultStr = String.valueOf(resultInt);

        /*
          将字符串转为数组，最终加入链表
        */
        char[] chars = resultStr.toCharArray();
        ListNode l3 = new ListNode(Integer.parseInt(String.valueOf(chars[chars.length-1])));
        ListNode result = l3;
        for(int i=chars.length-2; i>=0; i--) {
            ListNode nodeNext = new ListNode(Integer.parseInt(String.valueOf(chars[i])));
            result.next = nodeNext;
            l3 = result;
        }

        return l3;
    }
}
