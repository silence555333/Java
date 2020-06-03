package com.learn.generics;


import java.util.ArrayList;
import java.util.List;

public class GenericTest {
    public static  void main(String[] args){
        List<String> name=new ArrayList<>();
        List<Number> number=new ArrayList<>();
        name.add("Alice");
        number.add(234);
        getData(name);
        getData(number);
    }
    public static  void getData(List<?> data){
        System.out.println("data :" +data.get(0));
    }
}
