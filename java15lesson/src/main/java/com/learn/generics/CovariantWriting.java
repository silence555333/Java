package com.learn.generics;

import java.util.ArrayList;
import java.util.List;

public class CovariantWriting {
    static List<String> str=new ArrayList<>();
    static List<Object> obj=new ArrayList<>();

    static class  CovariantWriter<T>{
        void write(List<? super  T> list,T item){
            list.add(item);
        }
    }
    static void cotest(){
        CovariantWriter<String> strWriter=new CovariantWriter<>();
        strWriter.write(obj,"Test");
        strWriter.write(str,"Test");

    }
    public static  void main(String[] args){
//        test();
        cotest();
    }


}
