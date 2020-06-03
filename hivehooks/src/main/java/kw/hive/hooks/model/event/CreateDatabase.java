package kw.hive.hooks.model.event;

import java.util.Objects;

/**
 * @author yangfei
 * @create 2020-06-01 17:34
 */
public class CreateDatabase  extends BaseEvent{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CreateDatabase(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateDatabase that = (CreateDatabase) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "CreateDatabase{" +
                "name='" + name + '\'' +
                '}';
    }
}
