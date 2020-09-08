package com.data.lineage.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangfei
 * @create 2020-06-19 14:35
 */
public class DBUtil {
    public enum DB_TYPE {
        META, TASK
    }
    private String driver;// 驱动
    private String url;// url
    private String user;// 用户
    private String password;//密码
    private Connection conn;//连接

    public DBUtil(DB_TYPE type) {
//        this.driver =PropertyFileUtil.getProperty(type.name().toLowerCase() + ".jdbc.driverClassName");
//        this.url = PropertyFileUtil.getProperty(type.name().toLowerCase() + ".jdbc.url");
//        this.user = PropertyFileUtil.getProperty(type.name().toLowerCase() + ".jdbc.username");
//        this.password = PropertyFileUtil.getProperty(type.name().toLowerCase() + ".jdbc.password");
        this.driver="com.mysql.jdbc.Driver";
        this.url="jdbc:mysql://192.168.100.201:3306/hive";
        this.user="hive";
        this.password="hive";

    }
    private void setConn() {
        try {
            Class.forName(driver);
            this.conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException classnotfoundexception) {
            classnotfoundexception.printStackTrace();
            System.err.println("db: " + classnotfoundexception.getMessage());
        } catch (SQLException sqlexception) {
            System.err.println("db.getconn(): " + sqlexception.getMessage());
        }
    }
    public int doInsert(String sql) throws Exception{
        return doUpdate(sql);
    }

    public int doDelete(String sql) throws Exception{
        return doUpdate(sql);
    }

    public int doUpdate(String sql) throws Exception {
        Statement stmt = null;
        try {
            setConn();
            stmt = conn.createStatement();
            return stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            close(null,stmt);
        }
    }
    public List<Map<String, Object>> doSelect(String sql) throws Exception{
        Statement stmt = null;
        ResultSet rs = null;
        try {
            setConn();
            stmt = conn.createStatement(
                    java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
                    java.sql.ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(sql);
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            while (rs.next()) {
                Map<String, Object> map = rowToMap(rs, rs.getRow());
                list.add(map);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            close(rs,stmt);
        }
    }
    private Map<String, Object> rowToMap(ResultSet resultset, int rowNum) throws SQLException {
        Map<String, Object> map = new HashMap<String, Object>();
        ResultSetMetaData rsmd = resultset.getMetaData();
        int columnNum = rsmd.getColumnCount();
        for (int i = 1; i <= columnNum; i++) {
            String columnName = rsmd.getColumnLabel(i);
            map.put(columnName, resultset.getObject(columnName));
        }
        return map;
    }

    /**
     * @Function :Close all the statement and conn int this instance and close
     * 	the parameter ResultSet
     * @param rs
     * @param stmt
     * @throws Exception
     */
    public void close(ResultSet rs, Statement stmt) throws Exception {
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
    public static void main(String[] args) {
        try {
            DBUtil db = new DBUtil(DB_TYPE.META);
            List<Map<String, Object>> rs = db.doSelect("select * from version limit 5");
            for (Map<String, Object> map : rs) {
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    System.out.print(entry.getKey() + ":" + entry.getValue()+",");
                }
                System.out.println("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
