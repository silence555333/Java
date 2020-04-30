package com.learn.event;

/**
 * 消费发布者
 */
public class Publisher {
    public EventRegistry events;
    public  Publisher(){
        this.events=new EventRegistry("change");
    }
    public void  change(){
        events.notify("change");
    }

}
