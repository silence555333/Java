package com.design.links;

import java.util.Scanner;

/**
 * @author yangfei
 * @create 2020-07-27 20:24
 * 基于单链表实现LRU算法
 */
public class LRUBasedLinkedList<T> {

    /**
     * 默认链表容量
     */
    private final static Integer DEFAULT_CAPACITY =10 ;

    /**
     * 头结点
     */
    private SNode<T> headNode;

    /**
     * 链表长度
     */
    private Integer length;

    /**
     * 链表容量
     */
    private Integer capacity;


    public LRUBasedLinkedList(){
        this.headNode = new SNode<T>();
        this.capacity =DEFAULT_CAPACITY;
        this.length=0;

    }
    public LRUBasedLinkedList(Integer capacity){
        this.headNode=new SNode<T>();
        this.capacity = capacity;
        this.length=0;
    }

    public void add(T data){
        SNode preNode = findPreNode(data);
        //链表中存在，删除原数据，再插入到链表的头部
        if(preNode != null ){
            deleteElemOptim(preNode);
            insertElemAtBegine(data);
        }else {
            if(length >= this.capacity){
                //删除尾结点
                deleteElemAtEnd();
            }
            insertElemAtBegine(data);
        }
    }

    private void deleteElemAtEnd() {
        SNode prenode = headNode;

        if (prenode.getNext()==null){
            return;
        }

        //倒数第二个
        while (prenode.getNext().getNext()!=null){
            prenode=prenode.next;
        }

        SNode temp= prenode.getNext();
        temp.setNext(null);
        temp=null;
        length--;
    }

    /**
     * 链表头部插入节点
     * @param data
     */
    private void insertElemAtBegine(T data) {

        SNode next = headNode.getNext();
        headNode.setNext(new SNode(data,next));
        length++;
    }

    /**
     * 这一块注意。链表的自身结构是可以获取到头部节点的
     * 所以使用LRU可以最快的从头部查找到常用的数据
     * @param data
     * @return
     */
    private SNode findPreNode(T data) {
        SNode node=headNode;
        while (node.getNext() != null){
            if(data.equals(node.getNext().getElement())){
                return node;
            }
            node = node.getNext();

        }
     return  null;
    }

    private void deleteElemOptim(SNode preNode) {
        SNode temp = preNode.getNext();
        preNode.setNext(temp.getNext());
        temp =null;
        length--;
    }


    private void printAll(){
        SNode node = headNode.getNext();

        while (node!=null){
            System.out.println(node.getElement() + ",");
            node=node.getNext();
        }
        System.out.println();
    }

    class SNode<T>{
        private T element;

        private SNode next;

        public SNode(T element) {
            this.element = element;
        }

        public SNode(T element, SNode next) {
            this.element = element;
            this.next = next;
        }

        public SNode() {
            this.next=null;
        }

        public T getElement() {
            return element;
        }

        public void setElement(T element) {
            this.element = element;
        }

        public SNode getNext() {
            return next;
        }

        public void setNext(SNode next) {
            this.next = next;
        }
    }

    public  static  void  main(String[] args) {
        LRUBasedLinkedList list = new LRUBasedLinkedList();
        Scanner sc = new Scanner(System.in);
        while (true){
            list.add(sc.nextInt());
            list.printAll();
        }
    }
}
