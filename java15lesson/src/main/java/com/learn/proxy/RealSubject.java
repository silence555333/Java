package com.learn.proxy;



public class RealSubject implements Subject {

    @Override
    public String doAction(String name) {
        System.out.println("real subject do action"+name);
        return "SUCCESS";
    }
}
