package com.learn.loadclass;

import java.lang.reflect.Method;

public class MyClassTest {
    public  static  void main(String[] args) throws ClassNotFoundException{
        MyClassloader loader=new MyClassloader();
        Class<?> aClass=loader.findClass("com.learn.loadclass.MyClass");

        try{
            Object obj=aClass.newInstance();
            Method method=aClass.getMethod("show");
            method.invoke(obj);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
