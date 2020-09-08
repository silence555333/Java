package kw.data.util;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangfei
 * @create 2020-06-16 20:29
 */
public class DBUtil {
    private static final String URL = "jdbc:mysql://192.168.200.115:3306/test";
    private static final String UNAME = "kcredit";
    private static final String PWD = "kcredit";

    private static Connection conn = null;

    static
    {
        try
        {
            // 1.加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            // 2.获得数据库的连接
            conn = DriverManager.getConnection(URL, UNAME, PWD);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static Connection getConnection()
    {
        return conn;
    }

    /**
     * @Function :Close all the statement and conn int this instance and close
     * 	the parameter ResultSet
     * @param rs
     * @param stmt
     * @throws Exception
     */
    public static  void close(ResultSet rs, Statement stmt) throws Exception {
        if (rs != null) {
            rs.close();
        }
        if (stmt != null) {
            stmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

}
