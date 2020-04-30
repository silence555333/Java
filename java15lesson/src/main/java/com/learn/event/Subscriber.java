package com.learn.event;

/**
 * 定义一个消息接受者
 */
public class Subscriber {
    public void  handle(String eventType){
        System.out.println("changed");
    }
}
