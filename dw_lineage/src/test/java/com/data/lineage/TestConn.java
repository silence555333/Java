package com.data.lineage;

import com.data.lineage.bean.ColumnNode;

import com.data.lineage.exception.DBException;

import com.data.lineage.util.DBUtil;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yangfei
 * @create 2020-06-22 11:11
 */
public class TestConn extends TestCase {


    public void  testgetColumn(){
//        String sqlWhere  = "is_effective=1 and data_name='" + table + "'" + (Check.isEmpty(db) ? " " : (" and datastorage_name='"+db+"'"));
        List<ColumnNode> colList = new ArrayList<ColumnNode>();
        String sql="select b.DB_ID,b.`NAME` as datastorage_name " +
                ",a.tbl_id as data_id," +
                "a.TBL_NAME as data_name," +
                "d.CD_ID as column_id ," +
                "d.COLUMN_NAME as column_name \n" +
                "from tbls a join dbs b\n" +
                "on a.DB_ID =b.DB_ID\n" +
                "join sds c\n" +
                "on a.SD_ID = c.SD_ID\n" +
                "join columns_v2 d\n" +
                "on c.CD_ID=d.CD_ID" ;
//        String sql = "SELECT rc.column_id,rc.column_name,rd.data_id,rd.data_name,rd.datastorage_name FROM r_data_column rc join " +
//                "(SELECT data_id,data_name,datastorage_name from r_data where " + sqlWhere + ") rd " +
//                "on rc.data_id=rd.data_id ORDER BY rc.column_position";

        try {
            DBUtil db=new DBUtil(DBUtil.DB_TYPE.META);
            List<Map<String, Object>> rs = db.doSelect(sql);
            for (Map<String, Object> map : rs) {
                System.out.println((Long) map.get("column_id") + " name " + map.get("column_name")
                +"col_id" + map.get("data_id") +"col_name "+map.get("data_name") );
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DBException(sql, e);
        }
    }


}
