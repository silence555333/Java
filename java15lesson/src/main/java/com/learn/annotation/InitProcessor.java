package com.learn.annotation;

import annotation.InitMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 */
public class InitProcessor {
   public  static void main(String[] args) throws ClassNotFoundException,IllegalAccessException, InvocationTargetException,InstantiationException {
       /**
        *
        */
       Class<?>  clazz=Class.forName("annotation.InitDemo");
       //反射机制：先通过Class.forname()加载类拿到class信息，通过getMethos()拿到所有的public的方法
       Method[] methods=clazz.getMethods();
       if (methods!=null){
           for (Method method:methods){
               boolean isInitMethod=method.isAnnotationPresent(InitMethod.class);//判读一个方法是否标记为InitMethod,如果是，则创建一个对象并调用
               if (isInitMethod){
                   method.invoke(clazz.newInstance(),null);
                   Annotation annotation=method.getAnnotation(InitMethod.class);
                   InitMethod im=(InitMethod) annotation;
                   System.out.println(im.flag());
               }
           }
       }
   }
}
