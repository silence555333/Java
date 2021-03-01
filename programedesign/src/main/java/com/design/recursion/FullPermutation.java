package com.design.recursion;

import java.util.*;

import static javax.swing.UIManager.put;

/**
 * @author yangfei
 * @version 1.0
 * @Description TODO
 * @create 2021-02-26 14:35
 * 全排列的递归实现方式
 * <p>
 * 问题： 计算 1 2 3 有多少种排列方式
 */
public class FullPermutation {

    public static Map<Integer, List<String>> map = new LinkedHashMap<>();

    public void initmap() {
        char[] chars = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'
                , 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        System.out.println("英文字母" + chars.length);

        for (int i = 0; i < chars.length; i++) {
            int index = (i / 3) + 2;

            if (map.get(index) == null) {
                List<String> tmp = new ArrayList<>();
                tmp.add(String.valueOf(chars[i]));
                map.put(index, tmp);
            } else {
                List<String> tmp = map.get(index);
                tmp.add(String.valueOf(chars[i]));
                map.put(index, tmp);
            }

        }
    }

    public static void main(String[] args){
        FullPermutation fullPermutation=new FullPermutation();
        fullPermutation.initmap();
        List<String> res=fullPermutation.letterCombinations_leetcode("2");
        System.out.println("返回的列表长度为"+res.size());
        for (String s:res){
            System.out.println(s);
        }
    }

    /**
     * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。
     * <p>
     * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
     *
     * 刚开始考虑的解法复杂度太高，有3n
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param digits
     * @return
     */
    public List<String> letterCombinations(String digits) {
        List<String> res=new ArrayList<>();

        return letterrec(digits,res,0);




    }

    public List<String> letterrec(String digits,List<String> res,int index) {
        if(index>(digits.length()-1)){
            return res;
        }
        List<String> rec=new ArrayList<>();
        String number= String.valueOf(digits.charAt(index));
        Integer tmp = Integer.valueOf(number);

        List<String> needadd=map.get(tmp);


        if(res.size()==0||res.isEmpty()){
            for (String ne:needadd){
                rec.add(ne);
            }
        }else {
            for (String re : res) {
                for (String ne:needadd){
                    rec.add(re+ne);
                }
            }
        }
        return  letterrec(digits,rec,index+1);

    }

    public void printPermutations(int[] data, int n, int k) {

        if (k == 1) {
            for (int i = 0; i < n; ++i) {
                System.out.print(data[i] + " ");
            }
            System.out.println();
        }

        for (int i = 0; i < k; ++i) {
            int tmp = data[i];
            data[i] = data[k - 1];
            data[k - 1] = tmp;
            printPermutations(data, n, k - 1);
            tmp = data[i];
            data[i] = data[k - 1];
            data[k - 1] = tmp;
        }
    }


    public List<String> letterCombinations_leetcode(String digits) {
        List<String> combinations = new ArrayList<String>();
        if (digits.length() == 0) {
            return combinations;
        }
        Map<Character, String> phoneMap = new HashMap<Character, String>() {{
            put('2', "abc");
            put('3', "def");
            put('4', "ghi");
            put('5', "jkl");
            put('6', "mno");
            put('7', "pqrs");
            put('8', "tuv");
            put('9', "wxyz");
        }};
        backtrack(combinations, phoneMap, digits, 0, new StringBuffer());
        return combinations;
    }

    private void backtrack(List<String> combinations, Map<Character, String> phoneMap, String digits, int index, StringBuffer combination) {

        if(index==digits.length()){
            combinations.add(combination.toString());
        }else {
            Character in = digits.charAt(index);
            String needrec = phoneMap.get(in);

            for (int i = 0; i < needrec.length(); i++) {
                combination.append(needrec.charAt(i));
                backtrack(combinations, phoneMap, digits, index + 1, combination);
                combination.deleteCharAt(index);
            }
        }

    }


}
