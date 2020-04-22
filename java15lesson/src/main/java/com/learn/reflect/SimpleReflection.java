package com.learn.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射的基本应用
 */
public class SimpleReflection {
    private int temp;
    public int result;
    public  SimpleReflection(){

    }
    public int doSomething(){
        return 0;
    }
    private  int realDoSpmething(){
        return result;
    }

    public static  void main(String[] args){
        Class<?> clz;
        try{
            clz=Class.forName("com.learn.reflect.SimpleReflection");
            //可获取到除去构造函数的其他方法
            Method ms[]=clz.getDeclaredMethods();
            for (Method m:ms){
                System.out.println(m.toString());
            }
            //获取构造函数
            Constructor<?>[] cts=clz.getConstructors();
            for (Constructor<?> ct:cts){
                System.out.println(ct.toString());
            }
            //获取属性
            Field[] flds=clz.getDeclaredFields();
            for (Field fld:flds){
                System.out.println(fld.toString());
            }
            //获取父类信息
            Method ms2[]=clz.getSuperclass().getDeclaredMethods();
            for (Method m:ms2){
                System.out.println(m.toString());
            }
            //获取从父类继承下来的方法
            Method ms3[]=clz.getSuperclass().getMethods();
            for (Method m:ms3){
                System.out.println("父类继承方法 "+m.toString());
            }

        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
