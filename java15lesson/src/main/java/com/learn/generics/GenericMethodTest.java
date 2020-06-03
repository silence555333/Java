package com.learn.generics;

/**
 * 泛型方法改造
 */
public class GenericMethodTest {
    //<E> 为一个通用的抽象类型
    //printArray()
    public static <E> void printArray(E[] eArray){
        for (E e:eArray){
            System.out.println(e);
            }
    }
    public static void main(String args[]){
        Integer[] iArray={1,2,3,4,5};
        Double[] dArray={1.618,2.71828,3.14159};
        String[] sArray={"I","lover","beijing","tiananmen"};

        //编译器将这三种输出全部转换为printArray(Object[])
        // 编译出错，因为编译器中玩 了一个手脚，表面上提供了泛型，其实内部用了object
        //这种技术称之为erasure(类型擦除),这么做的主要考量是不想变动虚拟机，保持向后的兼容性
        printArray(iArray);
        printArray(dArray);
        printArray(sArray);

    }
}
