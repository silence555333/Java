package kw.data.hooks.model.event;

import kw.data.hooks.model.Table;

import java.util.Objects;

/**
 * @author yangfei
 * @create 2020-06-01 17:39
 */
public class RenameTable extends BaseEvent {

    private Table oldTable;
    private Table newTable;

    public RenameTable(Table oldTable, Table newTable) {
        this.oldTable = oldTable;
        this.newTable = newTable;
    }

    public RenameTable() {
    }

    public Table getOldTable() {
        return oldTable;
    }

    public void setOldTable(Table oldTable) {
        this.oldTable = oldTable;
    }

    public Table getNewTable() {
        return newTable;
    }

    public void setNewTable(Table newTable) {
        this.newTable = newTable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RenameTable that = (RenameTable) o;
        return Objects.equals(oldTable, that.oldTable) &&
                Objects.equals(newTable, that.newTable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(oldTable, newTable);
    }

    @Override
    public String toString() {
        return "RenameTable{" +
                "oldTable=" + oldTable +
                ", newTable=" + newTable +
                '}';
    }
}
