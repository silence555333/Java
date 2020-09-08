import java.math.BigDecimal;

/**
 * @author yangfei
 * @create 2020-06-02 19:26
 */
public class TEST {
    public static void main(String[] args) {

//        test1(-5);
//        test2(-3);
        calProfit();
    }

    private static void test1(int a){
        assert a > 0;
        System.out.println(a);
    }
    private static void test2(int a){
        assert a < 0 : "something goes wrong here, a cannot be less than 0";
        System.out.println(a);
    }

    public static void calProfit(){

        System.out.println(5000*0.045/12*6);

    }
}
