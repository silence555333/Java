import com.design.dynamic.Dynamic;
import com.design.tree.TreeUtil;
import junit.framework.TestCase;

/**
 * @author yangfei
 * @create 2020-07-06 15:01
 */
public class TestTree extends TestCase {

    public void testGetTreeAllPaths() {
        int[][] arrs = new int[3][3];
        arrs[0] = new int[]{0,0};
        arrs[1] = new int[]{1,0};
        arrs[2] = new int[]{0,0};
//        arrs[2] = new int[]{0, 0, 0};
//        TreeUtil tu = new TreeUtil();
//        int res = tu.uniquePathsWithObstacles(arrs);
        Dynamic sy=new Dynamic();
       int res= sy.uniquePathsWithObstacles(arrs);
        System.out.println("输出res "+res);

    }
}
