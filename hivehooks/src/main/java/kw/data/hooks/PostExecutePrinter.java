package kw.data.hooks;


import kw.data.hooks.model.DependencyKeyComp;
import org.apache.hadoop.hive.conf.HiveConf.ConfVars;
import org.apache.hadoop.hive.metastore.api.FieldSchema;
import org.apache.hadoop.hive.metastore.api.Partition;
import org.apache.hadoop.hive.ql.hooks.*;
import org.apache.hadoop.hive.ql.hooks.HookContext.HookType;
import org.apache.hadoop.hive.ql.hooks.LineageInfo.BaseColumnInfo;
import org.apache.hadoop.hive.ql.hooks.LineageInfo.Dependency;
import org.apache.hadoop.hive.ql.hooks.LineageInfo.DependencyKey;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.hadoop.hive.ql.session.SessionState.LogHelper;
import org.apache.hadoop.security.UserGroupInformation;

import java.util.*;
import java.util.Map.Entry;

/**
 * @author yangfei
 * @create 2020-06-18 17:50
 */
public class PostExecutePrinter implements ExecuteWithHookContext {

    public PostExecutePrinter() {
    }

    public void run(HookContext hookContext) throws Exception {
        assert hookContext.getHookType() == HookType.POST_EXEC_HOOK;

        Set<ReadEntity> inputs = hookContext.getInputs();
        Set<WriteEntity> outputs = hookContext.getOutputs();
        LineageInfo linfo = hookContext.getLinfo();
        UserGroupInformation ugi = hookContext.getUgi();
        this.run(hookContext.getConf().get(ConfVars.HIVEQUERYSTRING.varname), hookContext.getQueryPlan().getOperationName(), inputs, outputs, linfo, ugi);
    }


    public void run(String query, String type, Set<ReadEntity> inputs, Set<WriteEntity> outputs, LineageInfo linfo, UserGroupInformation ugi) throws Exception {
        LogHelper console = SessionState.getConsole();
        if (console != null) {
            if (query != null) {
                console.printInfo("POSTHOOK: query: " + query.trim(), false);
            }

            if (type != null) {
                console.printInfo("POSTHOOK: type: " + type, false);
            }

            printEntities(console, inputs, "POSTHOOK: Input: ");
            printEntities(console, outputs, "POSTHOOK: Output: ");
            if (linfo != null) {
                LinkedList<Entry<DependencyKey, Dependency>> entry_list = new LinkedList(linfo.entrySet());
                Collections.sort(entry_list, new DependencyKeyComp());
                Iterator iter = entry_list.iterator();

                while (true) {
                    Dependency dep;
                    DependencyKey depK;
                    do {
                        if (!iter.hasNext()) {
                            return;
                        }

                        Entry<DependencyKey, Dependency> it = (Entry) iter.next();
                        dep = (Dependency) it.getValue();
                        depK = (DependencyKey) it.getKey();
                    } while (dep == null);

                    StringBuilder sb = new StringBuilder();
                    sb.append("POSTHOOK: Lineage: ");
                    if (!depK.getDataContainer().isPartition()) {
                        sb.append(depK.getDataContainer().getTable().getTableName());
                    } else {
                        Partition part = depK.getDataContainer().getPartition();
                        sb.append(part.getTableName());
                        sb.append(" PARTITION(");
                        int i = 0;

                        FieldSchema fs;
                        for (Iterator i$ = depK.getDataContainer().getTable().getPartitionKeys().iterator(); i$.hasNext(); sb.append(fs.getName() + "=" + (String) part.getValues().get(i++))) {
                            fs = (FieldSchema) i$.next();
                            if (i != 0) {
                                sb.append(",");
                            }
                        }

                        sb.append(")");
                    }

                    sb.append("." + depK.getFieldSchema().getName() + " " + dep.getType() + " ");
                    sb.append("[");
                    Iterator i$ = dep.getBaseCols().iterator();

                    while (i$.hasNext()) {
                        BaseColumnInfo col = (BaseColumnInfo) i$.next();
                        sb.append("(" + col.getTabAlias().getTable().getTableName() + ")" + col.getTabAlias().getAlias() + "." + col.getColumn() + ", ");
                    }

                    sb.append("]");
                    console.printInfo(sb.toString(), false);
                }
            }
        }
    }

    static void printEntities(LogHelper console, Set<?> entities, String prefix) {
        List<String> strings = new ArrayList<String>();
        for (Object o : entities) {
            strings.add(o.toString());
        }
        Collections.sort(strings);
        for (String s : strings) {
            console.printError(prefix + s);
        }
    }

}
