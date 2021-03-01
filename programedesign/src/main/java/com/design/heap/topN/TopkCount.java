package com.design.heap.topN;

import java.util.PriorityQueue;

/**
 * @author yangfei
 * @version 1.0
 * @Description TODO
 * @create 2021-02-28 15:21
 * 进行求topK的问题
 */
public class TopkCount {

    public int[] topk(int[] data,int k){
        PriorityQueue<Integer> queue=new PriorityQueue<>(k);

        for (int i=0;i<data.length;i++){
            if(queue.size()<k){
                queue.offer(data[i]);
            }else {
                int value =queue.peek();
                if(data[i] > value){
                    queue.poll();
                    queue.offer(data[i]);
                }
            }
        }

        int[] result=new int[k];
        int index=0;

        while (!queue.isEmpty()){
            result[index++]=queue.poll();
        }
        return  result;
    }
}
