package com.learn.generics;

public class GenericClass<T> {
    private T t;

    public T get() {
        return t;
    }

    public void put(T t) {
        this.t = t;
    }

    public GenericClass(T t) {
        this.t = t;
    }

    public static  void  main(String[] args){
//        GenericClass<Integer> iClass=new GenericClass<Integer>();
//        GenericClass<String> sClass=new GenericClass<String>();
//        iClass.put(new Integer(10));
//        System.out.println(iClass.get());
//        sClass.put(new String("test"));
//        System.out.println(sClass.get());

        //判断iClass 和sClass 是否为一个类

//        Class GenericClassInteger=iClass.getClass();
//        Class GenericClassString=sClass.getClass();
//        System.out.println(GenericClassInteger);
//        System.out.println(GenericClassString);
//        if (GenericClassInteger.equals(GenericClassString)){
//            System.out.println("the same");
//        }
        /**
         * 结果显示是同一个类，意味着运行时的泛型类型都擦除了。
         * 既然java泛型只是一个编译时，编译之后类型被擦除了，那么可不可以用别的方法骗一下，把一个不用的类型的值塞给另一个类型
         * 反射：运行时动态起作用的，可以骗过泛型编译
         * 比如ArrayList<String> </String>，我们直接使用list.add(100) 视图插入一个数值时，编译出错
         * 我们可以使用反射
         * Class<?> clz=list.getClass();
         * Method m;
         * m=clz.getMethod("add",Object.class)
         * m.invoke(name,100)
         */


    }
}

