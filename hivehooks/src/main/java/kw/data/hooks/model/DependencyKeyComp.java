package kw.data.hooks.model;

import org.apache.hadoop.hive.ql.hooks.LineageInfo;

import java.util.Comparator;
import java.util.Map;

/**
 * @author yangfei
 * @create 2020-07-07 17:33
 */
public class DependencyKeyComp implements
        Comparator<Map.Entry<LineageInfo.DependencyKey, LineageInfo.Dependency>> {
    @Override
    public int compare(Map.Entry<LineageInfo.DependencyKey, LineageInfo.Dependency> o1, Map.Entry<LineageInfo.DependencyKey, LineageInfo.Dependency> o2) {
        if (o1 == null && o2 == null) {
            return 0;
        }
        else if (o1 == null && o2 != null) {
            return -1;
        }
        else if (o1 != null && o2 == null) {
            return 1;
        }
        else {
            // Both are non null.
            // First compare the table names.
            int ret = o1.getKey().getDataContainer().getTable().getTableName()
                    .compareTo(o2.getKey().getDataContainer().getTable().getTableName());

            if (ret != 0) {
                return ret;
            }

            // The table names match, so check on the partitions
            if (!o1.getKey().getDataContainer().isPartition() &&
                    o2.getKey().getDataContainer().isPartition()) {
                return -1;
            }
            else if (o1.getKey().getDataContainer().isPartition() &&
                    !o2.getKey().getDataContainer().isPartition()) {
                return 1;
            }

            if (o1.getKey().getDataContainer().isPartition() &&
                    o2.getKey().getDataContainer().isPartition()) {
                // Both are partitioned tables.
                ret = o1.getKey().getDataContainer().getPartition().toString()
                        .compareTo(o2.getKey().getDataContainer().getPartition().toString());

                if (ret != 0) {
                    return ret;
                }
            }

            // The partitons are also the same so check the fieldschema
            return (o1.getKey().getFieldSchema().getName().compareTo(
                    o2.getKey().getFieldSchema().getName()));
        }
    }
}
