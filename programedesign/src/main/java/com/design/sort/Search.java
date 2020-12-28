package com.design.sort;

/**
 * @author yangfei
 * @version 1.0
 * @Description TODO
 * @create 2020-12-27 15:28
 */
public class Search {
    /**
     * 二分查找
     * 假设数组是有序的
     * a为数组，low为小的，hight为大的
     */
    public static int bSearch(int[] a, int n, int value) {

        if (a.length < 1) return 0;
        int low = 0;
        int hight = n - 1;

        int res = -1;
        int mid = (low + hight) / 2;
        /**
         * 自己的方法，复杂度没有降低，至少为n
         * for(int i=0;i<a.length;i++){
         *             b=(low+hight)/2;
         *             System.out.println(b);
         *             if(a[b]>value){
         *                 hight=b;
         *             }else if(a[b]<value){
         *                 low=b;
         *             }else if(a[b]==value){
         *                 res=1;
         *             }
         *         }
         */


        while (low <= hight) {
            //改进：两个数特别大的时候，容易溢出，所以改为
            //或者为位运算 low+((high-low)>>1)
            mid = (low + (hight - low)) / 2;
            if (a[mid] > value) {
                hight = mid - 1;
            } else if (a[mid] < value) {
                low = mid + 1;
            } else {
                return 1;
            }

        }

        return -1;
    }

    public static void main(String[] args) {
        int[] t = new int[5];
        t[0] = 1;
        t[1] = 2;
        t[2] = 3;
        t[3] = 4;
        t[4] = 6;

//      int res= bSearch(t,t.length,2);
        bSearchMain(t, t.length, 2);

//      System.out.println(res);

    }

    public static void bSearchMain(int[] a, int n, int value) {
        bsearchIntenery(a, 0, n - 1, value);

    }

    private static int bsearchIntenery(int[] a, int low, int hight, int value) {
        if (low > hight) return -1;
        int mid = low + ((hight - low) >> 1);
        if (a[mid] == value) {
            return mid;
        } else if (a[mid] < value) {
            return bsearchIntenery(a, mid + 1, hight, value);
        } else {
            return bsearchIntenery(a, low, mid - 1, value);
        }
    }


}
