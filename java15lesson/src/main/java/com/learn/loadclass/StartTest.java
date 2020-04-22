package com.learn.loadclass;

public class StartTest {
    static {System.out.println("Tester static code execute");}
    static Stub testerStaticObject=new Stub("Tester static object -");

    {System.out.println("Tester  code execute");}

    Stub  testerObject=new Stub("Tester code create object -");

    public  static void  main(String[] args){
        System.out.println(" main() execute");
        Child c=new Child();
        c.sayHello();
        ((Parent)c).sayHello();
    }
}
