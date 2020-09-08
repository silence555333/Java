import junit.framework.TestCase;
import kw.data.util.DBUtil;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangfei
 * @create 2020-06-22 10:43
 */
//public class TestMysqlConnect extends TestCase {

//  public void testgetConnect() throws SQLException {
//      String sql= "select * from test.synlib limit 10";
//      Statement stmt = null;
//      ResultSet rs = null;
//      Connection conn = DBUtil.getConnection();
//      try {
//
//          stmt = conn.createStatement(
//                  java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
//                  java.sql.ResultSet.CONCUR_READ_ONLY);
//          rs = stmt.executeQuery(sql);
//          List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//          ResultSetMetaData rsmd = rs.getMetaData();
//          int columnNum = rsmd.getColumnCount();
//          for (int i = 1; i <= columnNum; i++) {
//              String columnName = rsmd.getColumnLabel(i);
//              System.out.println("columns "+columnName );
//          }
//
//      } catch (Exception e) {
//          e.printStackTrace();
//
//      } finally {
//        conn.close();
//      }
//  }


//}
