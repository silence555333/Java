package kw.hive.hooks.model.event;

import java.util.Objects;

/**
 * @author yangfei
 * @create 2020-06-01 17:38
 */
public class DropDatabase  extends BaseEvent{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DropDatabase() {
    }

    public DropDatabase(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DropDatabase that = (DropDatabase) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "DropDatabase{" +
                "name='" + name + '\'' +
                '}';
    }
}
