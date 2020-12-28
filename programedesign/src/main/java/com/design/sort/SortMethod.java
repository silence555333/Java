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

    /**
     * 归并排序
     * @param a
     * @param n
     */
  public static void merge_sort(int[] a,int n){

        merge_sort_c(a,0,n-1);


  }

    private static  void merge_sort_c(int[] a,int p,int r) {
      if (p>=r) return;
      int q=(p+r)/2;
      merge_sort_c(a,p,q);
      merge_sort_c(a,q+1,r);
      merge(a,p,q,r);

    }

    /**
     * 将分开的数组进行合并
     * @param a
     * @param p
     * @param q
     * @param r
     */
    private static void merge(int[] a, int p, int q, int r) {
        int i=p;
        int j=q+1;
        int k=0;

        int[] tmp=new int[r-p+1];
        while (i<=q&&j<=r){
            if(a[i]<=a[j]){
                tmp[k++]=a[i++];

            }else{
                tmp[k++]=a[j++];

            }
        }
        //判断是否有数组没有循环完
        int start=i;
        int end=q;

        if(j<=r){
            start=j;
            end=r;
        }

        while (start<=end){
            tmp[k++] = a[start++];
        }
        //将数据放回原数组
        for (i=0;i<=r-p;++i){
            a[p+i]=tmp[i];
        }
    }

    /**
     * 快排
     * 分为三个部分，q为中间的分区点
     * 然后p->q-1;q+1->r
     * @param a
     * @param n
     */
    public static void quickSort(int[] a ,int n){

        quickSort_c(a,0,n-1);

    }

    private static void quickSort_c(int[] a,int p,int r ) {
        if(p>=r) return;

        //获取分区点
       int q=partition(a,p,r);

       quickSort_c(a,p,q-1);
       quickSort_c(a,q+1,r);
    }

    private static int partition(int[] a, int p, int r) {

        int pivot=a[r];
        int  j =p;


        /**
         * 1，以pivot 为分界线，然后，定义两个数组，一个存放小于pivot
         * 一个存放大于pivot的，之后再合并
         * 实现之后，堆栈溢出
         */

        /**
         * 保持原数组不变，i为实现循环数组，j为定位
         * a[i]与pivot 进行比较，比pivot小的，则与j进行交换 j++
         * 当循环结束到pivot，则将a[j] 与pivot 交换
         */
        for(int i=p;i<r;++i ){
            if(a[i]<pivot){
                if(i==j){
                    ++j;
                }else {
                    int tmp = a[j];
                    a[j++] = a[i];
                    a[i] = tmp;
                }
            }

        }

        int tmp=a[j];
        a[j]=a[r];
        a[r]=tmp;
        System.out.println("j="+j);
        return j;
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
//        insertionSort(t,5);
        //归并排序
//        merge_sort(t,t.length);
        //快速排序
//          quickSort(t,t.length);
        //计数排序
        countingSort(t,t.length);
        for (int i=0;i<t.length;i++){
            System.out.println("排名后 ----"+t[i]);
        }

    }


    /**
     * 计数排序
     * 给定一个数组，先确定数组内的数据范围，如最小为0 ，最大为5
     * 根据数据范围，初始化一个相同长度的计数数组，统计不同大小对应的数量
     * 然后倒序遍历原数组，结合计数数组，将数据写入，即排序完成
     * @param a 非负整数的长度
     * @param n 数组长度
     */
    public static void countingSort(int[] a ,int n){
        //
        if(n<=1) return;

        //查找数据范围
        int max=a[0];

        for(int i=0;i<n;i++){
            System.out.println("数组中的值"+a[i]);
            if(a[i]>max){
                max=a[i];
            }
        }
        System.out.println("最大值为"+max);
        //计数数组
        int[] c=new int[max+1];

        //初始化数组每个数值为0
        for(int i=0;i<=max;i++){
            c[i]=0;
        }

        //计数，每一个数对应的数量
        for(int i=0;i<n;i++){
            int cc=a[i];
            c[cc]=c[cc]+1;
            System.out.println("数组中"+cc+"对应的值为" +c[cc]);
        }
        //对计数数组进行总的数量求和

        for(int i=0;i<c.length;i++){
            if(i<c.length-1){
                int tmp=c[i]+c[i+1];
                c[i+1]=tmp;
            }
            System.out.println("计数数组中"+i+"对应的值为" +c[i]);
        }

        int[] tmp=new int[n];

        System.out.println("中间数组的数值"+tmp.length);
        //遍历数组，进行排序
        for(int i=n-1;i>0;--i){
            int cc=a[i];
            System.out.println("原数组对应的数值"+cc);
            System.out.println("计数数组对应的数值"+c[cc]);
            int num=c[cc]-1;
            System.out.println("数值时多少"+num);
            tmp[num]=cc;
            System.out.println("开始排序数组"+num +"数值"+cc);
            //计数数组同时-1
            c[cc]=c[cc]-1;
        }
        for(int i=n-1;i>0;--i){
            a[i]=tmp[i];
        }
    }

    /**
     * 桶排序，场景举例：对100万个客户的年龄排名
     * 假设年龄范围为0-120，则建立120个桶
     * 每个桶放入对应的数值
     * 遍历桶，得到最后的排序
     *
     * @param arr
     * @param bucketSize
     */
    public static void bucketSort(int[] arr,int bucketSize){
        if(arr.length<2) return;;
        //计算数组的最小值
        int min=arr[0];

        //计算数组的最大值
        int max=arr[1];
        for (int i=0;i<arr.length;i++){
            if(max<arr[i]){
                max=arr[i];
            }else if(min>arr[i]){
                min=arr[i];
            }
        }


        //每个桶的范围
        int rangr=(max-min)/bucketSize;
        //每个桶的数量
        int bucketCount=(max-min)/bucketSize+1;
        int[][] buckets=new int[bucketCount][bucketSize];
        int[] indexArr=new int[bucketCount];

        //分配数组数据到桶里
        for(int i=0;i<arr.length;i++){
            int bucketIndex=(arr[i]-min)/bucketSize;
            /**
             * 这里对每个桶的大小是假定分配均等的
             * 如果发生了数据倾斜，这个时候需要对数组进行扩容
             */
            if(indexArr[bucketIndex] == buckets[bucketIndex].length){

                ensureCapacity(buckets,bucketIndex);
            }
            buckets[bucketIndex][indexArr[bucketIndex]++]=arr[i];

        }

        //对每个桶进行排序，这里使用了快排
        int k=0;
        for (int i=0;i<buckets.length;i++){
            if(indexArr[i]==0){
                continue;
            }
            quickSort_c(buckets[i],0,indexArr[i]-1);

            for (int j=0;j<indexArr[i];j++){
                arr[k++]=buckets[i][j];
            }
        }


    }

    /**
     * 数组扩容
     * 一般的数组扩容*2
     * @param buckets
     * @param bucketIndex
     */
    private static void ensureCapacity(int[][] buckets, int bucketIndex) {
        int[] tempArr=buckets[bucketIndex];
        int[] newArr=new int[tempArr.length*2];
        for (int j=0;j<tempArr.length;j++){
            newArr[j]=tempArr[j];
        }
        buckets[bucketIndex]=newArr;
    }


}
