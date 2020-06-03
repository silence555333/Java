package com.learn.generics;

import java.util.Arrays;
import java.util.List;

/**
 * 解决协变的问题
 */
public class CovariantReading {
    static List<String> str= Arrays.asList("Test string");
    static  List<Object> obj=Arrays.asList(new Object());

    static class CovariantReader<T>{
        T read(List< ? extends  T> list){
            return list.get(0);
        }
    }
    static void cotest(){
        CovariantReader<Object> objReader=new CovariantReader<>();
        Object o=objReader.read(obj);
        String s= (String) objReader.read(str);
    }
    static  class Reader<T>{
        T read(List<T> list){
            return list.get(0);
        }
    }
    static  void test(){
        Reader<Object> objectReader=new Reader<>();
        Object o=objectReader.read(obj);
//        String s=objectReader.read(str);

    }
    public static  void main(String[] args){
//        test();
        cotest();
    }
}
