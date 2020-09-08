package com.design.links;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangfei
 * @create 2020-07-27 20:22
 * 基于数组实现的LRU缓存
 * 时间复杂度O(n)
 * 空间复杂度O(n)
 * 不支持null的缓存
 */
public class LRUBasedArray<T> {

    //关于位运算符无非也就 与(&)、或(|)、异或(^)、取反(~)、左移(<<)、右移(>>)、无符号右移(>>>)
    /**
     * 左移运算
     * 由此我们得出一个快速的算法    M << n   其实可以这么算   M << n  = M * 2的n次方
     * 关于负数或者正数来说，移位的时候是一样的，但是在补位的时候，如果最高位是0就补0，如果最高位是1就补1
     *
     * 由此我们得出一个快速的算法    M >> n   其实可以这么算   M >> n  = M / 2^n
       无符号右移(>>>)只对32位和64位有意义
     *
     * 在移动位的时候与右移运算符的移动方式一样的，区别只在于补位的时候不管是0还是1，都补0
     */
    private static final int DEFAULT_CAPACITY = (1 << 3); //使用位运算 ，每一次的数组扩增是当前的二倍
    private int capacity;

    private int count;

    private T[] value;

    private Map<T,Integer> holder;

    public LRUBasedArray() {
        this(DEFAULT_CAPACITY);
    }

    public LRUBasedArray(int capacity) {

        this.capacity = capacity;
        value = (T[]) new  Object[capacity];
        count =0;
        holder = new HashMap<T, Integer>(capacity);
    }

    /**
     * 模拟访问某个值
     */
    public void offer(T object){
        if (object == null){
            throw new IllegalArgumentException("该缓存容器不支持null");
        }
        Integer index = holder.get(object);

        if (index == null){
            if (isFull()){
                removeAndCache(object);
            }else {
                cache(object,count);
            }
        }else {
            update(index);
        }
    }

    private void update(Integer end) {
        T target = value[end];
        rightShift(end);
        value[0] = target;
        holder.put(target,0);
    }

    /**
     * 缓存数据到头部，但要先右移
     * @param object
     * @param end
     */
    private void cache(T object, int end) {
        rightShift(end);
        value[0] = object;
        holder.put(object,0);
        count++;


    }
    public boolean isContain(T object){
        return holder.containsKey(object);
    }

    private void rightShift(int end) {
        for (int i=end-1;i>=0;i--){
            value[i+1] = value[i];
            holder.put(value[i],i+1);
        }
    }

    /**
     * 缓存满的情况，踢出后，再缓存到数组头部
     * @param object
     */
    private void removeAndCache(T object) {
        T key=value[--count];
        holder.remove(key);
        cache(object,count);

    }

    private boolean isFull() {
        return capacity == count;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(value[i]);
            sb.append(" ");
        }
        return sb.toString();
    }

    static class TestLRUBasedArray {

        public static void main(String[] args) {
            testDefaultConstructor();
            testSpecifiedConstructor(4);
//            testWithException();
        }

        private static void testWithException() {
            LRUBasedArray<Integer> lru = new LRUBasedArray<Integer>();
            lru.offer(null);
        }

        public static void testDefaultConstructor() {
            System.out.println("======无参测试========");
            LRUBasedArray<Integer> lru = new LRUBasedArray<Integer>();
            lru.offer(1);
            lru.offer(2);
            lru.offer(3);
            lru.offer(4);
            lru.offer(5);
            System.out.println(lru);
            lru.offer(6);
            lru.offer(7);
            lru.offer(8);
            lru.offer(9);
            System.out.println(lru);
        }

        public static void testSpecifiedConstructor(int capacity) {
            System.out.println("======有参测试========");
            LRUBasedArray<Integer> lru = new LRUBasedArray<Integer>(capacity);
            lru.offer(1);
            System.out.println(lru);
            lru.offer(2);
            System.out.println(lru);
            lru.offer(3);
            System.out.println(lru);
            lru.offer(4);
            System.out.println(lru);
            lru.offer(2);
            System.out.println(lru);
            lru.offer(4);
            System.out.println(lru);
            lru.offer(7);
            System.out.println(lru);
            lru.offer(1);
            System.out.println(lru);
            lru.offer(2);
            System.out.println(lru);
        }
    }


}
