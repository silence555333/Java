package kw.data.dao;

import kw.data.entity.HiveLineage;
import kw.data.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @author yangfei
 * @create 2020-06-16 20:42
 */
public class HiveLineageDao implements CommonDao {

    @Override
    public void update(HiveLineage hl) throws SQLException {
// 获得数据库连接
        Connection conn = DBUtil.getConnection();

        String sql = "insert into t_table_column_dependency (db,tname,col,parent_db,parent_tname,parent_col,ptype,expr,create_time,update_time) values(?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement ptmt = conn.prepareStatement(sql);

        ptmt.setString(1, hl.getDb());
        ptmt.setString(2, hl.getTname());
        ptmt.setString(3, hl.getCol());
        ptmt.setString(4, hl.getParent_db());
        ptmt.setString(5, hl.getParent_tname());
        ptmt.setString(6, hl.getParent_col());
        ptmt.setString(7, hl.getPtype());
        ptmt.setString(8, hl.getExpr());
        ptmt.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
        ptmt.setTimestamp(10, new Timestamp(System.currentTimeMillis()));
        ptmt.execute();
    }
}
