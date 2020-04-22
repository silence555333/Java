package com.learn.annotation;

/**
 * 类加载器
 */
public class Test2 {
    public static  void  main(String[] args){
        try{
            Class.forName("DummyClass");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
