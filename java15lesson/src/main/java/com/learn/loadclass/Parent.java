package com.learn.loadclass;

/**
 * 测试类加载的过程
 */
public class Parent {
    static Stub parentStaticObject =new Stub("Parent static object -");
    static {System.out.println("parent static code execute");}

    {System.out.println("parent code execute");}

    Stub parentObject =new Stub("Parent code create object -");

    Stub stub;

    public Parent(){
        System.out.println("parent constructor execute");
        stub=new Stub("Parent constructor create object");

    }

    public void sayHello(){
        System.out.println("hello from Parent");
    }



}
