package kw.data.entity;

import java.sql.Timestamp;

/**
 * @author yangfei
 * @create 2020-06-16 20:37
 * hive 的血缘依赖存储实体类
 */
public class HiveLineage {
   private String db ;
   private String  tname ;
   private String  col ;
   private String  parent_db ;
   private String  parent_tname;
   private String parent_col ;
   private String ptype ;
   private String  expr;
   private Timestamp create_time;
   private Timestamp update_time ;

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public String getParent_db() {
        return parent_db;
    }

    public void setParent_db(String parent_db) {
        this.parent_db = parent_db;
    }

    public String getParent_tname() {
        return parent_tname;
    }

    public void setParent_tname(String parent_tname) {
        this.parent_tname = parent_tname;
    }

    public String getParent_col() {
        return parent_col;
    }

    public void setParent_col(String parent_col) {
        this.parent_col = parent_col;
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

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public Timestamp getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Timestamp update_time) {
        this.update_time = update_time;
    }

    @Override
    public String toString() {
        return "HiveLineage{" +
                "db='" + db + '\'' +
                ", tname='" + tname + '\'' +
                ", col='" + col + '\'' +
                ", parent_db='" + parent_db + '\'' +
                ", parent_tname='" + parent_tname + '\'' +
                ", parent_col='" + parent_col + '\'' +
                ", ptype='" + ptype + '\'' +
                ", expr='" + expr + '\'' +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                '}';
    }
}
