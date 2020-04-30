package com.learn.event.gj;

public class StateChangeListner2 implements CallableEventListener {
    MyEvent2 event;

    public StateChangeListner2(MyEvent2 event) {
        this.event = event;
    }

    @Override
    public String call() throws Exception {
        System.out.println("Listener -- Processing");
        System.out.println("Listener -- fire event "+event.getSourceMssage());
        return "SUCCESS";
    }
}
