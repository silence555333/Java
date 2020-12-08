package kw.kd.dss.test;




import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangfei
 * @version 1.0
 * @Description TODO
 * @create 2020-12-08 14:13
 */
public class TestVaile {

    private static Map<Long,Integer> status = new HashMap<>();

    public void method1(Long projectid) throws InterruptedException {
        System.out.println("method1-------");
        Thread.sleep(6000);
        if(status.get(projectid)==null){

        }else {
            status.remove(projectid);
        }

    }

    @Test
    public  void test1()  {
        Long projectid=1l;
        if(status.get(projectid)==null){
            System.out.println("该项目开始发布");
            status.put(projectid,1);
            try {
                method1(projectid);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("该项目方法正在运行，请勿重复请求");
        }

    }

    @Test
    public  void test2() throws InterruptedException {
        Long projectid=1l;
        if(status.get(projectid)==null){
            status.put(projectid,1);
        }else {
            System.out.println("该项目方法正在运行，不能进行删除");
        }

    }



}
