package com.design.stack;

import com.design.links.Linked;

/**
 * @author yangfei
 * @create 2020-08-03 19:46
 * 使用双向链表实现一个链式栈
 */
public class LinkedStack {


    public void addNode(Integer val, Linked linked){
        //判断是否为空
        if (linked==null){
            linked.setValue(val);
        }
        while (linked.getNextlink() == null){
            if(linked.getNextlink()==null){
                //如果下一个为空，则是最后一个链表
                Linked node=new Linked(val);
                node.setPrelink(linked);
                linked.setNextlink(node);
            }
        }

    }


}
