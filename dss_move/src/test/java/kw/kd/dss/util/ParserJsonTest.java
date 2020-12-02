package kw.kd.dss.util;

import org.junit.Test;

import java.util.List;

/**
 * @author yangfei
 * @version 1.0
 * @Description TODO
 * @create 2020-11-30 19:26
 */
public class ParserJsonTest {

    @Test
    public void parseJsonFile(){
        String file="/Users/wangyue/Desktop/testparse";

        FlowParserUtil flowParserUtil=new FlowParserUtil();
        List<String> list=flowParserUtil.parseJsonFile(file);
        for (String s:list){
            System.out.println("-----解析出的resourceid都有---"+s);
        }


    }
}
