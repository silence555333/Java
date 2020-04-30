package com.learn.event.gj;

/**
 * 修改为线程池之后测试
 */
public class TestEvent2 {
    public  static  void main(String[] args){
        Source2 source=new Source2();
        try{
            source.addStateChangeListener(Class.forName("com.learn.event.gj.StateChangeListner2"));
        }catch (ClassNotFoundException e){}
        source.changeState();
    }
}
