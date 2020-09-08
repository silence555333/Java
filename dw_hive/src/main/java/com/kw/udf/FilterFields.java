package com.kw.udf;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;
/**
 * @author yangfei
 * @create 2020-08-13 15:17
 * 过滤传入的字符串中为空的字符
 */
public final class FilterFields extends UDF {
    /**
     * 这里接收参数的类型必须是hadoop能支持的输入输出类型
     * */
    public static Text evaluate(final Text s) {
        String result="";
        if (s == null||s.getLength()==0||s.equals("")||s.toString().toLowerCase()=="null") {
            result="";
        }else {
            String res=s.toString();
            String res_filter=res.replaceAll("（","(").replaceAll("）",")")
                    .replaceAll("[\\s|\\\\\\|\"|\\\b|\\\t|！|!|\\”\\\\’\\“|\\\\'|\\\\‘]|[\\u0000-\\u001f]","");
            result=res_filter;
        }
        return new Text(result);
    }

    public static void main(String[] args)
    {
        System.out.print(evaluate(new Text("DEFS928dc7e6ef687d7cc0737b32605824a2（）'”‘“")));

    }
}
