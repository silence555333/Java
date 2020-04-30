package com.learn.event;

import java.util.EventListener;
import java.util.HashSet;
import java.util.Set;

public class Source {
    private int state=0;
    private String msg="";
    Set<EventListener> listeners=new HashSet<EventListener>();

    public void addStateChangeListener(StateChangeListener listener){
        listeners.add(listener);
    }
    public void notifyListener(){
        for (EventListener listener: listeners){
            try{
                ((StateChangeListener)listener).handleEvent(new MyEvent(this));
            } catch (Exception e){

            }
        }
    }

    public void changeState(){
        state=(state == 0 ? 1:0);
        msg="State Changed";
        notifyListener();
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
