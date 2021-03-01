package com.design.heap.mergefile;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author yangfei
 * @version 1.0
 * @Description TODO
 * @create 2021-03-01 13:38
 */
public class MergeFile {

    /**
     * 使用最小堆解法
     * O(Nlog(N))
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        Queue<ListNode> pq = new PriorityQueue<>((v1, v2) -> v1.val - v2.val);
        for (ListNode node: lists) {
            if (node != null) {
                pq.offer(node);
            }
        }

        ListNode dummyHead=new ListNode(0);
        ListNode tail=dummyHead;
        while (!pq.isEmpty()){
            ListNode minNode=pq.poll();
            tail.next=minNode;
            tail=minNode;
            if(minNode.next!=null){
                pq.offer(minNode.next);
            }
        }


        return dummyHead.next;
    }


    public ListNode mergeKLists_1(ListNode[] lists){

        int k=lists.length;
        ListNode dummyHead=new ListNode(0);
        ListNode tail=dummyHead;
        while (true){
            ListNode minNode=null;
            int minPointer=-1;
            for (int i=0;i<k;i++){
                if (lists[i]==null){
                    continue;
                }
                if(minNode==null || lists[i].val < minNode.val){
                    minNode=lists[i];
                    minPointer=i;
                }
            }
            if(minPointer==-1){
                break;
            }
            tail.next=minNode;
            tail=tail.next;
            lists[minPointer] =lists[minPointer].next;


        }
        return dummyHead.next;
    }


    public static void main(String[] args){
        NodeUtil nodeUtil=new NodeUtil();
        ListNode listNode=new ListNode();
        listNode.val=1;
        nodeUtil.add(4,listNode);
        nodeUtil.add(5,listNode);
        ListNode listNode2=new ListNode();
        listNode2.val=1;
        nodeUtil.add(3,listNode2);
        nodeUtil.add(4,listNode2);
        ListNode listNode3=new ListNode();
        listNode3.val=2;
        nodeUtil.add(6,listNode3);
        ListNode[] listNodes=new ListNode[]{listNode,listNode2,listNode3}
        ;
//        nodeUtil.printNode(listNode);
//        nodeUtil.printNode(listNode2);
//        nodeUtil.printNode(listNode3);
        MergeFile me=new MergeFile();
        ListNode li=me.mergeKLists(listNodes);
        nodeUtil.printNode(li);



    }




}
