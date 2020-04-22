package com.learn.loadclass;

public class Child  extends Parent {

    static Stub parentStaticObject=new Stub("Child static object -");

    static {System.out.println("Child static code execute");}

    {System.out.println("Child  code execute");}

    Stub stub;

    public  Child(){
        System.out.println("child construtor execute");
        stub=new Stub("Child constructor create object");
    }
    public  void  sayHello(){
        System.out.println("hello from child");
    }
}
