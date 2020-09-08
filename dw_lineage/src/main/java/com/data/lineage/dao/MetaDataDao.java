package com.data.lineage.dao;

import com.data.lineage.bean.ColumnNode;
import com.data.lineage.bean.TableNode;
import com.data.lineage.exception.DBException;
import com.data.lineage.util.Check;
import com.data.lineage.util.DBUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yangfei
 * @create 2020-06-19 14:35
 */
public class MetaDataDao {
    DBUtil dbUtil = new DBUtil(DBUtil.DB_TYPE.META);

    public List<ColumnNode> getColumn(String db, String table){
//        String sqlWhere  = "is_effective=1 and data_name='" + table + "'" + (Check.isEmpty(db) ? " " : (" and datastorage_name='"+db+"'"));
        List<ColumnNode> colList = new ArrayList<ColumnNode>();
        String sql="select b.DB_ID,b.`NAME` as datastorage_name " +
                ",a.tbl_id as data_id," +
                "a.TBL_NAME as data_name," +
                "d.CD_ID as column_id ," +
                "d.COLUMN_NAME as column_name \n" +
                "from TBLS a join DBS b\n" +
                "on a.DB_ID =b.DB_ID\n" +
                "join SDS c\n" +
                "on a.SD_ID = c.SD_ID\n" +
                "join COLUMNS_V2 d\n" +
                "on c.CD_ID=d.CD_ID " +
                "where b.`name`= '"+db+"'  and a.TBL_NAME  ='" +table+"'";
//        String sql = "SELECT rc.column_id,rc.column_name,rd.data_id,rd.data_name,rd.datastorage_name FROM r_data_column rc join " +
//                "(SELECT data_id,data_name,datastorage_name from r_data where " + sqlWhere + ") rd " +
//                "on rc.data_id=rd.data_id ORDER BY rc.column_position";

        try {
            List<Map<String, Object>> rs = dbUtil.doSelect(sql);
            for (Map<String, Object> map : rs) {
                ColumnNode column = new ColumnNode();
                column.setId((Long) map.get("column_id"));
                column.setColumn((String) map.get("column_name"));
                column.setTableId((Long) map.get("data_id"));
                column.setTable((String) map.get("data_name"));
                column.setDb((String) map.get("datastorage_name"));
                colList.add(column);
            }
            return colList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DBException(sql, e);
        }
    }

    public List<TableNode> getTable(String db, String table){
//        String sqlWhere  = "is_effective=1 and data_name='" + table + "'" + (Check.isEmpty(db) ? " " : (" and datastorage_name='"+db+"'"));
        List<TableNode> list = new ArrayList<TableNode>();

        String sql="select b.DB_ID,b.`NAME` as datastorage_name " +
                ",a.tbl_id as data_id," +
                "a.TBL_NAME as data_name"+
                "from tbls a join dbs b\n" +
                "on a.DB_ID =b.DB_ID\n" +
                "where b.name= '"+db+"'  and a.tab_name ='" +table+"'";
//        String sql = "SELECT data_id,data_name,datastorage_name from r_data where " + sqlWhere + "";
        try {
            List<Map<String, Object>> rs = dbUtil.doSelect(sql);
            for (Map<String, Object> map : rs) {
                TableNode tableNode = new TableNode();
                tableNode.setId((Long) map.get("data_id"));
                tableNode.setTable((String) map.get("data_name"));
                tableNode.setDb((String) map.get("datastorage_name"));
                list.add(tableNode);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DBException(sql, e);
        }
    }


}
