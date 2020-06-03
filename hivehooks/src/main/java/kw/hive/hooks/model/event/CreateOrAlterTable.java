package kw.hive.hooks.model.event;

import java.util.Objects;

/**
 * @author yangfei
 * @create 2020-06-01 17:36
 */
public class CreateOrAlterTable  extends  BaseEvent{
    private String dbName;
    private String tblName;

    public CreateOrAlterTable() {
    }

    public CreateOrAlterTable(String dbName, String tblName) {
        this.dbName = dbName;
        this.tblName = tblName;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getTblName() {
        return tblName;
    }

    public void setTblName(String tblName) {
        this.tblName = tblName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateOrAlterTable that = (CreateOrAlterTable) o;
        return Objects.equals(dbName, that.dbName) &&
                Objects.equals(tblName, that.tblName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dbName, tblName);
    }

    @Override
    public String toString() {
        return "CreateOrAlterTable{" +
                "dbName='" + dbName + '\'' +
                ", tblName='" + tblName + '\'' +
                '}';
    }
}
