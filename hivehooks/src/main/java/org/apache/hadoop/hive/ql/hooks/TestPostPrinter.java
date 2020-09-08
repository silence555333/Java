package org.apache.hadoop.hive.ql.hooks;

import kw.data.hooks.model.DependencyKeyComp;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.api.FieldSchema;
import org.apache.hadoop.hive.metastore.api.Partition;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.hadoop.security.UserGroupInformation;

import java.util.*;

/**
 * @author yangfei
 * @create 2020-07-10 14:24
 */
public class TestPostPrinter implements ExecuteWithHookContext {
    public class DependencyKeyComp implements
            Comparator<Map.Entry<LineageInfo.DependencyKey, LineageInfo.Dependency>> {

        @Override
        public int compare(Map.Entry<LineageInfo.DependencyKey, LineageInfo.Dependency> o1,
                           Map.Entry<LineageInfo.DependencyKey, LineageInfo.Dependency> o2) {
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
    @Override
    public void run(HookContext hookContext) throws Exception {
        assert(hookContext.getHookType() == HookContext.HookType.POST_EXEC_HOOK);
        Set<ReadEntity> inputs = hookContext.getInputs();
        Set<WriteEntity> outputs = hookContext.getOutputs();
        LineageInfo linfo = hookContext.getLinfo();
        UserGroupInformation ugi = hookContext.getUgi();
        this.run(hookContext.getConf().get(HiveConf.ConfVars.HIVEQUERYSTRING.varname),
                hookContext.getQueryPlan().getOperationName(),inputs,outputs,linfo,ugi);
    }
    public void run(String query, String type, Set<ReadEntity> inputs,
                    Set<WriteEntity> outputs, LineageInfo linfo,
                    UserGroupInformation ugi) throws Exception {

        SessionState.LogHelper console = SessionState.getConsole();

        if (console == null) {
            return;
        }

        if (query != null) {
            console.printInfo("POSTHOOK: query: " + query.trim(), false);
        }
        if (type != null) {
            console.printInfo("POSTHOOK: type: " + type, false);
        }

        PreExecutePrinter.printEntities(console, inputs, "POSTHOOK: Input: ");
        PreExecutePrinter.printEntities(console, outputs, "POSTHOOK: Output: ");
        String resultTest="Test by silence --- : ";
        // Also print out the generic lineage information if there is any
        if (linfo != null) {
            LinkedList<Map.Entry<LineageInfo.DependencyKey, LineageInfo.Dependency>> entry_list =
                    new LinkedList<Map.Entry<LineageInfo.DependencyKey, LineageInfo.Dependency>>(linfo.entrySet());
            Collections.sort(entry_list, new DependencyKeyComp());
            Iterator<Map.Entry<LineageInfo.DependencyKey, LineageInfo.Dependency>> iter = entry_list.iterator();
            while(iter.hasNext()) {
                Map.Entry<LineageInfo.DependencyKey, LineageInfo.Dependency> it = iter.next();
                LineageInfo.Dependency dep = it.getValue();
                LineageInfo.DependencyKey depK = it.getKey();

                if(dep == null) {
                    continue;
                }

                StringBuilder sb = new StringBuilder();
                sb.append("POSTHOOK: Lineage: ");
                if (depK.getDataContainer().isPartition()) {
                    Partition part = depK.getDataContainer().getPartition();
                    sb.append(part.getTableName());
                    sb.append(" PARTITION(");
                    int i = 0;
                    for (FieldSchema fs : depK.getDataContainer().getTable().getPartitionKeys()) {
                        if (i != 0) {
                            sb.append(",");
                        }
                        sb.append(fs.getName() + "=" + part.getValues().get(i++));
                    }
                    sb.append(")");
                }
                else {
                    sb.append(depK.getDataContainer().getTable().getTableName());
                }
                sb.append("." + depK.getFieldSchema().getName() + " " +
                        dep.getType() + " ");

                sb.append("[");
                for(LineageInfo.BaseColumnInfo col: dep.getBaseCols()) {
                    sb.append("("+col.getTabAlias().getTable().getTableName() + ")"
                            + col.getTabAlias().getAlias() + "."
                            + col.getColumn() + ", ");
                }
                sb.append("]");
                resultTest = resultTest + sb.toString();

                console.printInfo(sb.toString(), false);
            }
        }
        System.out.println("this result is :  --- "+resultTest);
    }
}
