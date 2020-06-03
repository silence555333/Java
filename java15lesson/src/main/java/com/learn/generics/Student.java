package com.learn.generics;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 泛型测试获取不知道是否有参数的父类的
 */
public class Student  extends GenericClass<String>{
    public Student(String s) {
        super(s);
    }

    public static void main(String args[]){
        Student s=new Student("zs");
        System.out.println(s.getClass());
        System.out.println(s.getClass().getSuperclass());
        System.out.println(s.getClass().getGenericSuperclass());
        ParameterizedType parameterizedType= (ParameterizedType) s.getClass().getGenericSuperclass();

        Type[] actualTypeArguments=parameterizedType.getActualTypeArguments();
        System.out.println(actualTypeArguments[0]);
        System.out.println(s.get());


    }
}
