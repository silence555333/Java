package com.learn.reflect;

import sun.misc.Perf;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class TestConcreteClass {
    public static  void getClassObj(){
        Class<?> concreteClass=ConcreteClass.class;
        concreteClass=new ConcreteClass(5).getClass();
        try{
            concreteClass=Class.forName("com.learn.reflect.ConcreteClass");
            System.out.println(concreteClass.getName());
        }catch (ClassNotFoundException e){

        }

    }

    public static void getSupperClass(){
        Class<?> superClass;
        try{
            superClass=Class.forName("com.learn.reflect.ConcreteClass").getSuperclass();
            System.out.println(superClass);
        }catch (ClassNotFoundException e){
//            e.printStackTrace();
        }
    }

    public static  void getInterface(){
        Class<?>[] interfaces;
        try{
            interfaces=Class.forName("com.learn.reflect.ConcreteClass").getInterfaces();
            System.out.println(interfaces[0]);
        }catch (ClassNotFoundException e){

        }
    }

    public static void getDeclaredClasses(){
        Class<?>[] explicitClasses;
        try{
            explicitClasses=Class.forName("com.learn.reflect.ConcreteClass").getDeclaredClasses();
            System.out.println(Arrays.toString(explicitClasses));
        }catch (SecurityException e){}
        catch (ClassNotFoundException e){}
    }

    public  static void  getPackageName(){
        try{
            System.out.println(Class.forName("com.learn.reflect.BaseInterface").getPackage().getName());

        }catch (ClassNotFoundException e){

        }
    }

    public  static void  getClassModifier(){
        System.out.println(Modifier.toString(ConcreteClass.class.getModifiers()));
        try{
            System.out.println(Modifier.toString(Class.forName("com.learn.reflect.BaseInterface").getModifiers()));

        }catch (ClassNotFoundException e){

        }
    }
    public  static void  getAllAnnotations(){
        java.lang.annotation.Annotation[] annotations;

        try{
            Method[] methods;
            methods=Class.forName("com.learn.reflect.ConcreteClass").getMethods();
            for (Method m:methods){
                annotations=m.getAnnotations();
                for (Annotation a:annotations){
                    System.out.println(m+":"+a);
                }
            }
            System.out.println(Modifier.toString(Class.forName("com.learn.reflect.BaseInterface").getModifiers()));

        }catch (ClassNotFoundException e){

        }
    }

    public static  void main(String[] args){
//        getClassObj();
//        getSupperClass();
//        getInterface();
//        getDeclaredClasses();
//        getPackageName();
//        getClassModifier();
        getAllAnnotations();
    }

}
