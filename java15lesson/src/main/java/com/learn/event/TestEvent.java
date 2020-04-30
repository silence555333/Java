package com.learn.event;

/**
 * 测试事件过程
 */
public class TestEvent {
    public static  void  main(String[] args){
        Source source=new Source();
        source.addStateChangeListener(new StateChangeListener());
        source.changeState();
    }
}
