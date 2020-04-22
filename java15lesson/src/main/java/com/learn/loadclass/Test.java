package com.learn.loadclass;

public class Test {
    public  static void  main(String[] args){
        System.out.println("---------");
        ClassLoader classLoader=Test.class.getClassLoader();
        System.out.println("=-----");

        ClassLoader classLoader1=classLoader.getParent();
        System.out.println(classLoader1);

        ClassLoader classLoader2=classLoader.getParent();
        System.out.println(classLoader2);
    }
}
