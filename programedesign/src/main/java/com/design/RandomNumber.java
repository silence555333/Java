package com.design;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * @author yangfei
 * @create 2020-05-14 21:07
 * 生成100万个不重复的随机数
 */
public class RandomNumber {
    //1,使用java自有的
    public static  void getRandom() {
        try {

            BufferedWriter out = new BufferedWriter(new FileWriter("/Users/wangyue/Desktop/yf_file/bwriter"));
            Random r = new Random(1);
            int[] numn=new int[1000000];
            for (int i = 0; i < 1000000; i++) {
                int ran1 = r.nextInt(10000000);
                numn[i]=ran1;
                out.write(ran1+"\r");
            }
            out.close();
            System.out.println("文件创建成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static  void main(String[] args){
        long starttime=System.currentTimeMillis();
        getRandom();
        long enttime=System.currentTimeMillis();

        System.out.println("输出计算的时间"+(enttime-starttime));

    }
    /**
     * 问题：如何生成0至n-1之间k个不同的随机顺序的随机整数
     */


}
