package com.java.basic;

/**
 * @author yangfei
 * @version 1.0
 * @Description TODO
 * @create 2020-11-11 18:28
 * 一些java的常识类应该知道的
 */
public class BasicKnowlegene {

    public void test1(){
        int a = 0;
        for (int i = 0; i < 99; i++) {
            a = a ++;
        }
        System.out.println("a: "+a);
    }

    public void test2(){
        int b = 0;
        for (int i = 0; i < 99; i++) {
            b =  ++b;
        }
        System.out.println("b:  "+b);
    }

    public void test3(){
        Integer a = 0;
        int b = 0;
        for (int i = 0; i < 99; i++) {
            a = a ++;
            b = a ++;
        }
        System.out.println(a);
        System.out.println(b);
    }
    public static  void main(String[] args){
       BasicKnowlegene bk=new BasicKnowlegene();
//       bk.test1();
       bk.test3();

    }

}
