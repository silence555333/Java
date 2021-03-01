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
        int[] t = new int[3];
        t[0] = 3;
        t[1] = 5;
        t[2] = 1;

//      int res= bSearch(t,t.length,2);
//        bSearchMain(t, t.length, 6);
        int ss=reserversearch(t,3);
        System.out.println(ss);


//      System.out.println(res);

    }

    public static void bSearchMain(int[] a, int n, int value) {
        int wz=bsearchInteneryFirst(a, 0, n - 1, value);
        System.out.println(wz);

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

    /**
     * 查找第一个值等于给定值的元素
     */
    private static int bsearchInteneryFirst(int[] a, int low, int hight, int value) {
        if (low > hight) return -1;
        int mid = low + ((hight - low) >> 1);
        System.out.println("mid--"+mid);
        if (a[mid] == value) {
//            int res=getFirst(a,mid,value);
            return bsearchIntenery(a, mid -1, mid, value);
//            return res;
        } else if (a[mid] < value) {
            return bsearchIntenery(a, mid + 1, hight, value);
        } else {
            return bsearchIntenery(a, low, mid - 1, value);
        }
    }


    /**
     * 查找第一个值等于给定值的元素
     */
    private static void bsearchInteneryF(int[] a,int n,int value){
        int low=0;
        int hight=n-1;
        int mid=(low+hight)/2;

        if(a[mid]==value){
            mid=mid-1;
           System.out.println("");
        }


    }
    private  static  int getFirst(int[] a, int mid, int value){
        int low=mid-1;
        if(a[low]==value){
          return   getFirst(a,low,value);
        }else{
            return mid;
        }
    }

    /**
     * 反转字符串查找
     * @return
     */
    private static  int reserversearch(int[] a,int targer){
        /**
         * 使用二分查找，寻找反转点
         * a[mid-1]>a[mid]||a[mid+1]<a[mid] 为反转点
         * 获取反转点，得到两个区间
         * 判断目标是否在区间，然后再使用二分查找
         */
        int n=a.length-1;
        if(n==0){
            return -1;
        }
        if(n==1){
            if(a[n]==targer){
                return n;
            }else {
                return -1;
            }
        }
        int l=0;
        int r=n-1;
        int mid=(l+r)/2;
        while (l<r){
            mid=(l+r)/2;
            if(a[mid]==targer){
                return mid;
            }
            if(a[0]<=a[mid]){
                if(a[0]<=targer&&a[mid]<targer){
                    r=mid-1;
                }else{
                    l=mid+1;
                }
            }else {

                if (a[mid] < targer && a[n-1] <= targer) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
        }
        return mid;
    }
}
