package com.learn.event.gj;

import java.util.EventObject;

public class ExStateChangeListener implements ExEventListener {
    @Override
    public void handleEvent(EventObject event) {
        System.out.println("Listener-"+((MyEventEx)event).getSourceMessage()+"_handled.");
    }
}
