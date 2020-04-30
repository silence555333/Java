package com.learn.reflect;

import java.lang.reflect.Method;

public class Calc {
    public Calc(){

    }
    public  int add(int a,int b){
        return a+b;
    }
    public int times(int a,int b){
        return a*b;
    }
    public static  void   main(String[] args){
        Class<?> cls2;
        try{
            cls2=Class.forName("com.learn.reflect.Calc");

            Class<?> partypes[]=new Class[2];

            partypes[0]=Integer.TYPE;
            partypes[1]=Integer.TYPE;
            Method meth=cls2.getMethod("add",partypes);

            Calc methobj=(Calc) cls2.newInstance();
            Object arglist[]=new Object[2];
            arglist[0]=new Integer(37);
            arglist[1]=new Integer(47);
            System.out.println(meth.invoke(methobj,arglist));

            meth=cls2.getDeclaredMethod("times",partypes);
            System.out.println(meth.invoke(methobj,arglist));

            //多次调用机器所发生的问题
            Object retobj;
            int i=0;
            long lstart=System.currentTimeMillis();
            while (i++<10000){
                retobj=meth.invoke(methobj,arglist);//calc.add()
            }
            long lend1=System.currentTimeMillis();
            System.out.println(lend1-lstart);

            Integer retInt;
            int j=0;
            long lstart2=System.currentTimeMillis();
            while(j++ < 10000){
                retInt=methobj.add(new Integer(37),new Integer(47));
            }
            long lend2=System.currentTimeMillis();
            System.out.println(lend2-lstart2);
        }catch (Exception e){

        }
    }
}
