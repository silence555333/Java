package com.learn.event.gj;

import com.learn.event.Source;

import java.util.EventObject;

/**
 * 先定义事件
 */
public class MyEventEx  extends EventObject {
    private static  final long serialVersionUID=1L;
    private int state;
    private String msg;


    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public MyEventEx(Object source) {
        super(source);
        state=((SourceEx)source).getState();
        msg=((SourceEx)source).getMessage();

    }

    public  int getSourceState(){
        return this.state;
    }

    public String getSourceMessage(){
        return this.msg;
    }
}
