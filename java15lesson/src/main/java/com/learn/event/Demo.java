package com.learn.event;

public class Demo {
    public static void main(String[] args){
        Publisher publisher=new Publisher();
        publisher.events.subscribe("change",new Subscriber());
        publisher.change();
    }
}
