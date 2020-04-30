package com.learn.event.gj;

import java.util.EventObject;

public class MyEvent2  extends EventObject {

    private static  final  long serialVersionUID=1L;
    private  int state;

    private String msg;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public MyEvent2(Object source) {
        super(source);
        state=((Source2)source).getState();
        msg=((Source2)source).getMessage();
    }

    public int getSourceState() {
        return this.state;
    }

    public String getSourceMssage() {
        return this.msg;
    }
}
