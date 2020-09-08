package com.design.queue;

/**
 * @author yangfei
 * @create 2020-08-26 14:33
 */
public class ArrayQueue {
    //数组：items,数组大小：n
    private String[] items;
    private int n=0;
    // head 表示队头小标，tail表示队尾下标
    private int head=0;
    private  int tail=0;
    //申请一个大小为capacity的数组
    public ArrayQueue(int capacity){
        items = new String[capacity];
        n=capacity;
    }

    //入队操作：考虑队列已经满的情况
    public boolean enqueue(String item){
        //tail==n && head ==0 表示整个队列都满了
        if(tail==n){
            if(head==0) return false;
            //数据搬移
            for (int i=head;i<tail;i++){
                items[i-head] = items[i];
            }
            //搬移完之后重新更新head和tail
            tail-=head;
            head=0;
        }
        items[tail]=item;
        ++tail;
        return true;
    }
    //出队
    public String dequeue(){
        if(head==tail) return null;
        String ret = items[head];
        ++head;
        return ret;
    }
}
