package kw.hive.hooks.model;

import java.util.Objects;

/**
 * @author yangfei
 * @create 2020-06-01 17:29
 */
public class PartitionKeyValue {
    private String partitionKey;
    private String partitionValue;

    public PartitionKeyValue() {
    }

    public String getPartitionKey() {
        return partitionKey;
    }

    public void setPartitionKey(String partitionKey) {
        this.partitionKey = partitionKey;
    }

    public String getPartitionValue() {
        return partitionValue;
    }

    public void setPartitionValue(String partitionValue) {
        this.partitionValue = partitionValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartitionKeyValue that = (PartitionKeyValue) o;
        return Objects.equals(getPartitionKey(), that.getPartitionKey()) &&
                Objects.equals(getPartitionValue(), that.getPartitionValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPartitionKey(), getPartitionValue());
    }

    @Override
    public String toString() {
        return "PartitionKeyValue{" +
                "partitionKey='" + partitionKey + '\'' +
                ", partitionValue='" + partitionValue + '\'' +
                '}';
    }
}
