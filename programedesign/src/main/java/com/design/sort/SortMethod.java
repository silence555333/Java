package com.design.sort;

/**
 * @author yangfei
 * @version 1.0
 * @Description TODO
 * @create 2020-10-13 14:38
 */
public class SortMethod {

//自己实现的冒泡排序，并不是最优，因为循环为n*n
    public static void bubbleSortBySelf(int[] a,int n) {
        if (n<1) return;

        for (int i=0;i<n;i++){
            for (int j=0;j<n-1;j++){
                if(a[j+1]<a[j]){
                    int tmp=a[j];
                    a[j]=a[j+1];
                    a[j+1]=tmp;
                }

            }
        }
        System.out.println(a.toString());
    }

    public static   void bubbleSort(int[] a ,int n){
        if (n<1) return;
        for (int i=0;i<n;++i){
            boolean flag=false;

            for (int j=0;j<n-i-1;++j){

            }
        }
    }

    public static  void insertionSort(int[] a,int n){
        if(n<1) return;
        for (int i=0;i<n;++i){
            int value=a[i];
            int j=i-1;
            for (;j>=0;--j){
                if(a[j]>value){
                    a[j+1]=a[j];
                }else {
                    break;
                }
            }
            a[j+1]=value;
        }
    }
    public static  void main(String[] args){
        int[] t=new int[5];
        t[0]=1;
        t[1]=7;
        t[2]=3;t[3]=2;t[4]=10;

        for (int i=0;i<t.length;i++){
            System.out.println("排名前 ----"+t[i]);
        }
//        bubbleSort(t,5);
        insertionSort(t,5);
        for (int i=0;i<t.length;i++){
            System.out.println("排名后 ----"+t[i]);
        }

    }
}
