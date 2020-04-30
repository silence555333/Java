package com.learn.event;

import java.util.EventListener;

/**
 * 定义事件监听者
 * 按照要求，所有的事件监听者要实现java.util.EventListener 接口
 * 这个接口是一个空的，只是一个标记
 * 习惯上会加一个handleEvent()方法，这个方法利用传递过来的事件消息进行相应的处理
 */
public class StateChangeListener  implements EventListener {
    public void handleEvent(MyEvent event){
        System.out.println(event.toString()+"fire event" +event.getSourceMessage());
    }
}
