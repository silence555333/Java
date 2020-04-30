package com.learn.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventRegistry {

    Map<String, List<Subscriber>>  subscribes=new HashMap<>();

    public EventRegistry(String eventType){
        this.subscribes.put(eventType,new ArrayList<Subscriber>());

    }

    public void  subscribe(String eventType,Subscriber subscriber){
        List<Subscriber> users=subscribes.get(eventType);
        users.add(subscriber);

    }

    public void notify(String evenType){
        List<Subscriber> users=subscribes.get(evenType);
        for (Subscriber subscriber : users){
            subscriber.handle(evenType);
        }
    }
}
