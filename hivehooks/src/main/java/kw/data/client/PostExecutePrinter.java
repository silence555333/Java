package kw.data.client;


import kw.data.hooks.model.DependencyKeyComp;
import org.apache.hadoop.hive.ql.hooks.*;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.hadoop.security.UserGroupInformation;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.hive.metastore.api.FieldSchema;
import org.apache.hadoop.hive.metastore.api.Partition;
import org.apache.hadoop.hive.ql.hooks.HookContext.HookType;
import org.apache.hadoop.hive.ql.hooks.LineageInfo.BaseColumnInfo;
import org.apache.hadoop.hive.ql.hooks.LineageInfo.Dependency;
import org.apache.hadoop.hive.ql.hooks.LineageInfo.DependencyKey;

import org.apache.hadoop.hive.ql.session.SessionState.LogHelper;



/**
 * @author yangfei
 * @create 2020-07-08 11:31
 */
public class PostExecutePrinter  implements PostExecute, ExecuteWithHookContext {


    @Override
    public void run(HookContext hookContext) throws Exception {
        assert(hookContext.getHookType() == HookType.POST_EXEC_HOOK);
        SessionState ss = SessionState.get();
        Set<ReadEntity> inputs = hookContext.getInputs();
        Set<WriteEntity> outputs = hookContext.getOutputs();
        LineageInfo linfo = hookContext.getLinfo();
        UserGroupInformation ugi = hookContext.getUgi();
        this.run(ss,inputs,outputs,linfo,ugi);
    }

    @Override
    public void run(SessionState sess, Set<ReadEntity> inputs,
                    Set<WriteEntity> outputs, LineageInfo linfo,
                    UserGroupInformation ugi) throws Exception {
        LogHelper console = SessionState.getConsole();

        if (console == null) {
            return;
        }

        if (sess != null) {
            console.printError("POSTHOOK: query: " + sess.getCmd().trim());
            console.printError("POSTHOOK: type: " + sess.getCommandType());
        }

        PreExecutePrinter.printEntities(console, inputs, "POSTHOOK: Input: ");
        PreExecutePrinter.printEntities(console, outputs, "POSTHOOK: Output: ");

        // Also print out the generic lineage information if there is any
        if (linfo != null) {
            LinkedList<Map.Entry<DependencyKey, Dependency>> entry_list =
                    new LinkedList<>(linfo.entrySet());
            Collections.sort(entry_list, new DependencyKeyComp());
            Iterator<Map.Entry<DependencyKey, Dependency>> iter = entry_list.iterator();
            while(iter.hasNext()) {
                Map.Entry<DependencyKey, Dependency> it = iter.next();
                Dependency dep = it.getValue();
                DependencyKey depK = it.getKey();

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
                for(BaseColumnInfo col: dep.getBaseCols()) {
                    sb.append("("+col.getTabAlias().getTable().getTableName() + ")"
                            + col.getTabAlias().getAlias() + "."
                            + col.getColumn() + ", ");
                }
                sb.append("]");

                console.printError(sb.toString());
            }
        }
    }
}
