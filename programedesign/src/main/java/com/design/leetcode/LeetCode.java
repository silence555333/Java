package com.design.leetcode;

import sun.jvm.hotspot.opto.InlineTree;

import java.util.*;

/**
 * @author yangfei
 * @create 2020-07-01 20:26
 */
public class LeetCode {

    /**
     * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
     * 说明：
     * <p>
     * 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
     *
     * @param nums 这个是我的实现思路
     *
     *  使用资源：
     *             执行用时 9ms
     *             内存消耗 40.4MB
     */
    public static int getOnlyNumber(int[] nums) {
        int result = 0;
        //
        Set<Integer> set = new HashSet();
        for (int i : nums) {
            if (set.contains(i)) {
                set.remove(i);
            } else {
                set.add(i);
            }
        }
        for (int im : set) {
            result = im;
        }

        System.out.println(result);
        return result;

    }

    /**
     * 别人更好的思路，使用异或
     *
     * @param nums
     * @return
     */
    public static int getOnlyNumberByOthers(int[] nums) {
        //使用位运算
        /**
         * 解释：在位运算的时候，同一位的数据如果相同，则会置为0
         * 在同一位不相同的时候，就会置为1
         * 1 001
         * 2 010
         * 结果 011
         * 5 0100
         * 结果 0111 6
         * 4 0011
         * 结果 0100 5
         * 4 0011
         * 结果 0111 6
         * 1 0001
         * 结果 0110 3
         * 2 0010
         * 结果 0100 5
         * 最终 结果 5
         *
         * 资源使用：
         *   执行用时 1ms
         *   内存消耗：41.1MB
         */
        int[] A = {1, 2, 5, 4, 4, 1, 2};
        int n = A[0];
        for (int i = 1; i < A.length; i++) {
            System.out.println( "before----" +n);
            n = n ^ A[i];
            System.out.println("after----"+n);
        }
        System.out.println(n);
        return n;
    }

    /**
     *
     * @param k
     * @param prices
     * 给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
     *
     * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 k 笔交易。
     * 注意: 你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）
     * 输入: [3,2,6,5,0,3], k = 2
     * 输出: 7
     * 解释: 在第 2 天 (股票价格 = 2) 的时候买入，在第 3 天 (股票价格 = 6) 的时候卖出, 这笔交易所能获得利润 = 6-2 = 4 。
     *      随后，在第 5 天 (股票价格 = 0) 的时候买入，在第 6 天 (股票价格 = 3) 的时候卖出, 这笔交易所能获得利润 = 3-0 = 3 。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-iv
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @return
     */
    public  int maxProfit(int k, int[] prices){
        int res=0;

        int result=0;
        //存放当前数组可以向后拓展的数据
        HashMap<Node,List<Relate>> init_cal= new HashMap<Node, List<Relate>>();

        //存放每种可能的所有队列,出现问题，最终一个node很多组合
        HashMap<String,List<Integer>> res_map=new HashMap<String, List<Integer>>();
        //存放最终的值
//        Set<Integer> result_l=new HashSet<Integer>();
        List result_l  = new ArrayList();
        for (int i=0;i< prices.length;i++){
            Node in =new Node();
            in.setIndex(i);
            in.setValue(prices[i]);
            List<Relate> pro=new ArrayList<Relate>();
            //一直计算在他之后的数据
            for (int j=i+1;j< prices.length;j++){
                int bef=prices[i];
                int after=prices[j];
                int profile=after-bef;
                //定义需要放的list

                if (profile>0){
                    Node out=new Node();
                    out.setIndex(j);
                    out.setValue(prices[j]);
                    Relate rel=new Relate();
                    rel.setIn(in);
                    rel.setOut(out);
                    pro.add(rel);
                }
            }
            //判断node的list是否不为空
            if(pro!=null && pro.size()>0){
                init_cal.put(in,pro);
            }
        }
        for (Node ini:init_cal.keySet()){
            System.out.println("第一次计算之后的数据结果"+ini.toString()+" ：  "+init_cal.get(ini));
        }


        //循环遍历init_cal
        for (Node key_init:init_cal.keySet()){
            //存储的可能以及开头的数据
            Node key=key_init;
            //获取key下面的可能列表
            List<Relate> val =init_cal.get(key);
            //列表中数据需要进行比较循环

            for (int i=0;i<val.size();i++ ){
                //获取数值
                Relate neext=val.get(i);
                Node in1=neext.getIn();
                //需要向下迭代的key
                Node out1=neext.getOut();

                int res_value1=out1.getValue()-in1.getValue();
                //判断结果集是否为空
                List<Integer> list_res=null;
                if(res_map.get(key_init.index+","+key_init.getValue()+","+i)==null){
                    list_res=new ArrayList<Integer>() ;
                }else {
                    list_res=res_map.get(key_init.index+","+key_init.getValue()+","+i);
                }
                list_res.add(res_value1);
                res_map.put(key_init.index+","+key_init.getValue()+","+i,list_res);
                //循环key 找出在它之后的
                for (Node in_af:init_cal.keySet()){
                    int ser_out1=out1.getIndex();
                    int ser_in_af=in_af.getIndex();
                    //在它之后
                    if(ser_out1<ser_in_af ){

                        List<Relate>  next2= init_cal.get(in_af);
                        //获取下面的数值
                        for (int j=0;j<next2.size();j++){

                            Relate rel2=next2.get(j);
                            int value2=rel2.out.getValue()-rel2.in.getValue();
                            //写入结果集合
                            List<Integer> list_res2=null;
                            if(res_map.get(key_init.index+","+key_init.getValue()+","+i)==null){
                                list_res2=new ArrayList<Integer>() ;
                            }else {
                                list_res2=res_map.get(key_init.index+","+key_init.getValue()+","+i);
                            }
                            list_res2.add(value2);
                            res_map.put(key_init.index+","+key_init.getValue()+","+i,list_res2);
                        }
                    }
                }

            }


        }

        //输出结果列表
        for (String i:res_map.keySet()){
            System.out.println("计算的从i开始 的数据"+i +"计算的可能的集合"+res_map.get(i));
            List<Integer> res_l=res_map.get(i);
            Integer rr=0;
            if(res_l.size()<k){
                for (int r:res_l){
                    rr=rr+r;
                }
            }else
            {
                for(int j=0;j<k;j++){
                    rr=rr+res_l.get(j);
                }
            }

            result_l.add(rr);
        }
        System.out.println("最大值" + getMaxDouble(result_l));
        //根据词数计算最大值

        res=getMaxDouble(result_l);

        return res;
    }

    public static Integer getMaxDouble(List<Integer> list){

        Integer max = 0;
        for(int i:list){
            if(i>max){
                max=i;
            }
        }
        return max;
    }

    static class Node{
        private Integer index;
        private Integer value;

        public Integer getIndex() {
            return index;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "index=" + index +
                    ", value=" + value +
                    '}';
        }
    }
    static  class Relate{
        private Node in;
        private Node out;

        public Node getIn() {
            return in;
        }

        public void setIn(Node in) {
            this.in = in;
        }

        public Node getOut() {
            return out;
        }

        public void setOut(Node out) {
            this.out = out;
        }
    }


    /**
     * 将一个按照升序排列的有序数组，转换为一棵高度平衡二叉搜索树。
     *
     * 本题中，一个高度平衡二叉树是指一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1。
     * @param
     * @return
     */
    class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x){ val =x;}
    }


    public static void main(String[] args) {
        int[] nums = new int[]{2, 2, 1};
        int[] pro=new int[]{-10,-3,0,5,9};

//        l.maxProfit(2,pro);
//        l.sortedArrayToBST(pro);
//        maxProfit(2,pro);
//        getOnlyNumberByOthers(nums);
//        maxProfit(2,pro);




    }






}
