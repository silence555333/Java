package com.design;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangfei
 * @create 2020-05-14 20:12
 */
public class Weiys {

    public static  void main(String[] args){
        //实现32位图
        int num=2;
        int[] number=new int[3];
        number[0]=12;
        number[1]=4;
        number[1]=6;
       int[] a =new int[32];

       for (int i=0;i<a.length-1;i++){
           for (int j=0;j<=number.length-1;j++) {
               if (i == number[j]) {
                   a[i] = 1;
               } else {
                   a[i] = 0;
               }
           }
       }

       System.out.println(a);


    }

}
