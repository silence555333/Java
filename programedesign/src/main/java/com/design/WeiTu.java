package com.design;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * @author yangfei
 * @create 2020-05-14 20:39
 * 使用位图进行数据排序
 */
public class WeiTu {
    //使用位图进行排序
    public static int[] bitmapSort(int[] arr){
        int max=arr[0];
        int min=max;

        for (int i:arr){
            if(max < i){
                max=i;
            }
            if (min>i){
                min=i;
            }
        }
        //初始化位图数组大小
        int temp=0;
        int[] newArr=null;
        if(min <0){
            temp=0-min;
            newArr=new int[max-min+1];
        }else{
            newArr=new int[max+1];
            min=0;
        }
//构建位图
        for (int i:arr){
            newArr[i+temp]++;
        }
        int index=0;

        for (int i=0;i< newArr.length;i++){
            while (newArr[i]>0){
                arr[index]=i+min;
                index++;
                newArr[i]--;
            }
        }

        return arr;
    }
    //使用java自带排序
    public static void getJavaSort(int[] arr){

        if (arr.length >= 2) {
            for (int i = 1; i < arr.length; i++) {
                //挖出一个要用来插入的值,同时位置上留下一个可以存新的值的坑
                int x = arr[i];
                int j = i - 1;
                //在前面有一个或连续多个值比x大的时候,一直循环往前面找,将x插入到这串值前面
                while (j >= 0 && arr[j] > x) {
                    //当arr[j]比x大的时候,将j向后移一位,正好填到坑中
                    arr[j + 1] = arr[j];
                    j--;
                }
                //将x插入到最前面
                arr[j + 1] = x;
            }
        }
    }

    public static  void main(String[] args){
        //100万条整数，大小于1000万
        Random r = new Random(1);
        int[] numn=new int[1000000];
        for (int i = 0; i < 1000000; i++) {
            int ran1 = r.nextInt(10000000);
            numn[i]=ran1;
        }
        System.out.println("已经生成数组");
        long starttime=System.currentTimeMillis();
        bitmapSort(numn);
        long enttime1=System.currentTimeMillis();
        getJavaSort(numn);
        long enttime2=System.currentTimeMillis();
        //输出计算的时间1146
        //使用位图计算 192
        System.out.println("输出位图计算的时间1"+(enttime1-starttime));
        //输出计算的时间2368
        //使用java 插入排序 2131401
        System.out.println("输出java 排序计算的时间2"+(enttime2-enttime1));

    }

    public  void writefile(){
        try {

            BufferedWriter out = new BufferedWriter(new FileWriter("/Users/wangyue/Desktop/yf_file/afterbwriter"));
            out.write("这是样例");
            out.close();
            System.out.println("文件创建成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
