package com.learn.proxy;

public class DynamicProxyTest {
    public static  void main(String[] args){
        SubjectDynamicProxy proxy=new SubjectDynamicProxy("RealSubject1");
        Subject p=proxy.getProxy();
        String retValue=p.doAction("do action");
        System.out.println(retValue);
//        String value=p.doS
    }
}
