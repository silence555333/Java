package kw.data.dao;

import kw.data.entity.HiveLineage;

import java.sql.SQLException;

/**
 * @author yangfei
 * @create 2020-06-16 20:27
 */
public interface CommonDao {

    public void update(HiveLineage hl) throws SQLException;


}
