package com.learn.event.gj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 监听器注册
 */
public class ListenerRegistry {
    Map<String, List<ExEventListener>> listeners=new HashMap<>();

    public ListenerRegistry(String eventType) {
        this.listeners.put(eventType,new ArrayList<ExEventListener>());
    }

    public void  addListener(String eventType,ExEventListener listener){
        List<ExEventListener> users=listeners.get(eventType);
        users.add(listener);
    }

    public void notifyListener(String eventType ,SourceEx source){
        List<ExEventListener> users=listeners.get(eventType);
        for (ExEventListener listener:users){
            System.out.println("Registry -listener");
            try{
                listener.handleEvent(new MyEventEx(source));
            }catch (Exception e){

            }
        }

    }

}
