package com.learn.generics;

/**
 * 测试多参数泛型
 */
public class Test {
    public static <K,V> boolean compare(Pair<K,V> p1,Pair<K,V> p2){
        return p1.getKey().equals(p2.getKey()) && p1.getValue().equals(p2.getValue());
    }
    public static  void  main(String[] args){
        Pair<Integer,String> p1=new Pair<>(1,"aaa");
        Pair<Integer,String> p2=new Pair<>(1,"bbb");
        System.out.println(Test.compare(p1,p2));
    }
}
