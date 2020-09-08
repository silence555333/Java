package com.leetcode;

/**
 * @author yangfei
 * @create 2020-07-14 08:58
 */
public class Solution {
    public void reverseString(char[] s) {

        char[] chr=new char[]{};

    }
    public char[] getChar(char[] chars,int index){
        char[] res= new char[chars.length];
        if(index-1 > 0) {
          chars[index]=chars[chars.length-index];
        }
        return  res;
    }

}
