package com.learn.event.gj;

public class TestEventEx {
    public static  void main(String[] args){
        SourceEx sourceEx=new SourceEx();
        ExEventListener listener=new ExStateChangeListener();
        ListenerRegistry registry=new ListenerRegistry("change");
        registry.addListener("change",listener);
        sourceEx.registry=registry;

        sourceEx.changeState();

    }
}
