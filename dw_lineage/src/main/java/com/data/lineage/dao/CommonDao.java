package com.data.lineage.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author yangfei
 * @create 2020-06-22 15:12
 */
public interface CommonDao {
    //    public void update(HiveLineage hl) throws SQLException;
    public List<Map<String, Object>> doSelect(String sql) throws SQLException;

    public int doUpdate(String sql) throws Exception;
}
