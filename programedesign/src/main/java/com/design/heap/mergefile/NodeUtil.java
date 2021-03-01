package com.design.heap.mergefile;

/**
 * @author yangfei
 * @version 1.0
 * @Description TODO
 * @create 2021-03-01 15:10
 */
public class NodeUtil {

    public void  add(int val,ListNode node){
        ListNode listNode=new ListNode(val,null);
        if(node==null){
            node=listNode;
            System.out.println("初始化"+node.val);
        }else {
            ListNode q=node;
            while (q.next!=null){
                q=q.next;
            }
            listNode.next=q.next;
            q.next=listNode;
        }

    }

    public void printNode(ListNode head){
        if(head!=null){
            System.out.println(head.val);
            ListNode node=head.next;
            printNode(node);//递归调用
        }
    }
}
