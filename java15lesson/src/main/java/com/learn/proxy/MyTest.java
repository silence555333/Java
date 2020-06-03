package com.learn.proxy;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class MyTest {
    public static void main(String[] args) throws ClassNotFoundException,IllegalAccessException,IllegalArgumentException, InvocationTargetException,InstantiationException {
        Class<?> clazz=Class.forName("ProxySubject");
        Object obj=clazz.newInstance();
        Field[] flds=clazz.getDeclaredFields();
        if (flds!=null){
            for (Field fld:flds){
                boolean isAutowired=fld.isAnnotationPresent(Autowired.class);
                if (isAutowired){
                    Class<?> concreteClass=Class.forName("RealSubject1");
                    fld.set(obj,concreteClass.newInstance());
                }
            }
        }
        ((ProxySubject)obj).doAction("Test");
    }
}
