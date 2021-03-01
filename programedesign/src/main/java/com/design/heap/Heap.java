package com.design.heap;

/**
 * @author yangfei
 * @version 1.0
 * @Description TODO
 * @create 2021-02-28 11:51
 *
 * 实现堆的计算
 */
public class Heap {

    private  int[] a;//数组，从下标1凯斯存储数据
    private  int n;//堆可以存储的最大数据个数
    private  int count;//堆中已经存储的数据个数

    public Heap(int capacity){
        a= new int[capacity+1];
        n=capacity;
        count=0;
    }

    public  void insert(int data){
        if(count>=n) return;//堆满了
        ++count;
        a[count]=data;
        int i=count;
        while (i/2 >0 && a[i]>a[i/2]){//自下往上堆化
            swap(a,i,i/2);
            i=i/2;

        }

    }

    /**
     * 完成数组中两个元素的交换
     * @param a
     * @param i
     * @param i1
     */
    private void swap(int[] a, int i, int i1) {
        int tmp=a[i];
        a[i]=a[i1];
        a[i1]=a[i];
    }


    public  void removeMax(){
        if(count ==0) return ;//堆中没有数据
        a[1]=a[count];
        --count;
        heapify(a,count,1);
    }

    private void heapify(int[] a, int count, int i) {
        while (true){
            int maxPos=i;
            if(i*2 <=n && a[i]<a[i*2]) maxPos=i*2;
            if(i*2+1<=n && a[maxPos] < a[i*2+1]) maxPos=i*2+1;
            if(maxPos ==i) break;
            swap(a,i,maxPos);
            i=maxPos;

        }


    }

    private  void buildHeap(int[] a ,int n){
        for (int i=n/2;i>=1;--i){
            heapify(a,n,i);
        }
    }

    public  void  sort(int[] a,int n){
        buildHeap(a,n);
        int k=n;
        while (k>1){
            swap(a,1,k);
            --k;
            heapify(a,k,1);
        }
    }





























}
