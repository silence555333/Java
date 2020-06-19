package com.data.lineage.bean;

/**
 * @author yangfei
 * @create 2020-06-19 14:47
 */
public class TableNode {
    private long id;
    private String table;
    private String db;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getTable() {
        return table;
    }
    public void setTable(String table) {
        this.table = table;
    }
    public String getDb() {
        return db;
    }
    public void setDb(String db) {
        this.db = db;
    }
}
