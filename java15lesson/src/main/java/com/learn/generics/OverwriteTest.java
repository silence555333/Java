package com.learn.generics;

/**
 * 为入门级别的方法
 */
public class OverwriteTest {
    public static  void  printArray(Double[] dArray){
        for (Double d:dArray){
            System.out.println(d);
        }
    }
    public static void printArray(String[] sArray){
        for (String s :sArray){
            System.out.println(s);
        }
    }

    public static void main(String args[]){
        Double[] dArray={1.618,2.71828,3.14159};
        String[] sArray={"I","lover","beijing","tiananmen"};
        printArray(dArray);
        printArray(sArray);
    }
}
