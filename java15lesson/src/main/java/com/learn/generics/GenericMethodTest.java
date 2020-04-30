package com.learn.generics;

/**
 * 泛型方法改造
 */
public class GenericMethodTest {
    public static <E> void printArray(E[] eArray){
        for (E e:eArray){
            System.out.println(e);
            }
    }
    public static void main(String args[]){

    }
}
