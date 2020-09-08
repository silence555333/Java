package com.design.links;

/**
 * @author yangfei
 * @create 2020-07-28 14:34
 * 单链表的插入，删除、查找
 */
public class SinglyLinkedList {


    private Node head =null;

    public Node findByValue(int value){
        Node p=head;
        while (p!=null && p.data!=value){
            p=p.next;
        }

        return p;
    }

    public Node findByIndex(int index){
        Node p=head;
        int pos=0;
        while(p!=null && pos!=index){
            p=p.next;
            ++pos;
        }
        return p;
    }


    public void insertToHead(int value){
        Node newNode=new Node(value,null);
        insertToHead(newNode);
    }

    public void insertToHead(Node newNode){

        if (head==null){
            head=newNode;
        }else {
            newNode.next=head;
            head=newNode;
        }
    }
    //顺序插入
    //链表尾部插入
    public void insertTail(int value){
        Node newNode = new Node(value,null);

    }

    public static class Node{
        private int data;
        private  Node next;

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

}
