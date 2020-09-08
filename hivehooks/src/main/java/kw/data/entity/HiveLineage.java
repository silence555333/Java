package kw.data.entity;

import java.sql.Timestamp;

/**
 * @author yangfei
 * @create 2020-06-16 20:37
 * hive 的血缘依赖存储实体类
 */
public class HiveLineage {
   private String target_db ;
   private String  target_tname ;
   private String  target_col ;
   private String  source_db ;
   private String  source_tname;
   private String source_col ;
   private String ptype ;
   private String  expr;
   private String query_id;
   private String query_sql;
   private String create_time;
   private String update_time ;

    public String getTarget_db() {
        return target_db;
    }

    public void setTarget_db(String target_db) {
        this.target_db = target_db;
    }

    public String getTarget_tname() {
        return target_tname;
    }

    public void setTarget_tname(String target_tname) {
        this.target_tname = target_tname;
    }

    public String getTarget_col() {
        return target_col;
    }

    public void setTarget_col(String target_col) {
        this.target_col = target_col;
    }

    public String getSource_db() {
        return source_db;
    }

    public void setSource_db(String source_db) {
        this.source_db = source_db;
    }

    public String getSource_tname() {
        return source_tname;
    }

    public void setSource_tname(String source_tname) {
        this.source_tname = source_tname;
    }

    public String getSource_col() {
        return source_col;
    }

    public void setSource_col(String source_col) {
        this.source_col = source_col;
    }

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    public String getExpr() {
        return expr;
    }

    public void setExpr(String expr) {
        this.expr = expr;
    }

    public String getQuery_id() {
        return query_id;
    }

    public void setQuery_id(String query_id) {
        this.query_id = query_id;
    }

    public String getQuery_sql() {
        return query_sql;
    }

    public void setQuery_sql(String query_sql) {
        this.query_sql = query_sql;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    @Override
    public String toString() {
        return "HiveLineage{" +
                "target_db='" + target_db + '\'' +
                ", target_tname='" + target_tname + '\'' +
                ", target_col='" + target_col + '\'' +
                ", source_db='" + source_db + '\'' +
                ", source_tname='" + source_tname + '\'' +
                ", source_col='" + source_col + '\'' +
                ", ptype='" + ptype + '\'' +
                ", expr='" + expr + '\'' +
                ", query_id='" + query_id + '\'' +
                ", query_sql='" + query_sql + '\'' +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                '}';
    }
}
