package com.learn.event;



import java.util.EventObject;

/**
 *
 */
//java.util 中提供了EventObject ,所有的事件都要继承它
public class MyEvent extends EventObject {

    private int state;
    private String msg;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public MyEvent(Object source) {
        super(source);
        state=((Source)source).getState();
        msg=((Source)source).getMsg();
        msg=((Source)source).getMsg();
    }
    public int getSourceState(){
        return this.state;
    }
    public String getSourceMessage(){
        return this.msg;
    }
}
