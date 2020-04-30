package com.learn.event.gj;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Source2 {
    private int state=0;
    private  String  msg="";
    Set<Class<?>>  listenerclss=new HashSet<Class<?>>();

    public void addStateChangeListener(Class<?> listener){
        listenerclss.add(listener);
    }
    public void notifyListener() throws NoSuchMethodException,SecurityException,InstantiationException,IllegalAccessException,IllegalArgumentException
    , InvocationTargetException {
        ExecutorService executorService= Executors.newCachedThreadPool();
        List<Future<String>> returnList=new ArrayList<Future<String>>();

        for (Class<?> listenercls : listenerclss){
            Class<?> parTypes[] =new Class[1];
            parTypes[0] = MyEvent2.class;
            Constructor<?> ct=listenercls.getConstructor(parTypes);
            Object arglist[]=new Object[1];
            arglist[0] =new MyEvent2(this);
            CallableEventListener eventListener=(CallableEventListener) ct.newInstance(arglist);

            Future<String> future=executorService.submit(eventListener);

            returnList.add(future);

        }
        for(Future<String> fs: returnList){
            try{
                while (!fs.isDone()){
                    System.out.println("Source -"+fs.get());
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                executorService.shutdown();
            }
        }
    }

    public void changeState(){
        state=(state==0 ? 1:0);
        msg="State Changed";
        System.out.println("Source- "+msg);

        try{
            notifyListener();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public int getState() {
        return this.state;
    }

    public String getMessage() {
        return this.msg;
    }
}
