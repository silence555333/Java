package com.learn.annotation;

public class InitDemo {
    @InitMethod(flag = "1")
    public void init(){
        System.out.println("init ...");
    }

}
