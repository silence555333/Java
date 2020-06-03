package com.learn.proxy;

public class ProxySubject implements Subject {
    @Autowired
    Subject subject;


    public ProxySubject() {
    }

    @Override
    public String doAction(String name) {
        System.out.println("proxy control");
        String rtnValue=subject.doAction(name);
        return "SUCCESS";
    }
}
