package com.learn.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理
 */
public class SubjectDynamicProxy {
    String subjectName="";
    private Subject subject=null;


    public SubjectDynamicProxy(String subjectName) {
        this.subjectName = subjectName;
        try{
            this.subject= (Subject) Class.forName(subjectName).newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Subject getProxy(){
        return (Subject) Proxy.newProxyInstance(SubjectDynamicProxy.class.getClassLoader(), subject.getClass().getInterfaces()
                , new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (method.getName().equals("doAction")){
                            System.out.println("Access Control");
                            return method.invoke(subject,args);
                        }

                        if (method.getName().equals("doSomething")){
                            System.out.println("other control");
                            return method.invoke(subject,args);
                        }
                        return null;
                    }
                });
    }
}
