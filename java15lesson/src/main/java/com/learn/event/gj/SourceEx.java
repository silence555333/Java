package com.learn.event.gj;

/**
 * 定义一个新的事件源
 */
public class SourceEx {
    private int state=0;
    private  String msg="";
    public ListenerRegistry registry;
    public void fireEvent(String event){
        System.out.println("Source - fire event -notify listener");
        registry.notifyListener(event,this);

    }
    public void  changeState(){
        state=(state==0 ? 1:0);
        msg="State changed";
        System.out.println("Source -"+msg);
        fireEvent("change");
    }

    public int getState() {
        return this.state;
    }

    public String getMessage() {
        return this.msg;
    }
}
