package kw.data.client;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.hadoop.hive.common.io.FetchConverter;
import org.apache.hadoop.hive.ql.hooks.ExecuteWithHookContext;
import org.apache.hadoop.hive.ql.hooks.HookContext;
import org.apache.hadoop.hive.ql.hooks.HookContext.HookType;
import org.apache.hadoop.hive.ql.hooks.ReadEntity;
import org.apache.hadoop.hive.ql.hooks.WriteEntity;
import org.apache.hadoop.hive.ql.plan.HiveOperation;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.hadoop.hive.ql.session.SessionState.LogHelper;
import org.apache.hadoop.security.UserGroupInformation;
/**
 * @author yangfei
 * @create 2020-07-08 11:39
 */
public class PreExecutePrinter implements ExecuteWithHookContext {

    @Override
    public void run(HookContext hookContext) throws Exception {
        assert(hookContext.getHookType() == HookType.PRE_EXEC_HOOK);
        SessionState ss = SessionState.get();
        if (ss != null && ss.out instanceof FetchConverter) {
            boolean foundQuery = ss.getHiveOperation() == HiveOperation.QUERY &&
                    !hookContext.getQueryPlan().isForExplain();
            ((FetchConverter)ss.out).foundQuery(foundQuery);
        }

        Set<ReadEntity> inputs = hookContext.getInputs();
        Set<WriteEntity> outputs = hookContext.getOutputs();
        UserGroupInformation ugi = hookContext.getUgi();
        this.run(ss,inputs,outputs,ugi);
    }

    public void run(SessionState sess, Set<ReadEntity> inputs,
                    Set<WriteEntity> outputs, UserGroupInformation ugi)
            throws Exception {

        LogHelper console = SessionState.getConsole();

        if (console == null) {
            return;
        }

        if (sess != null) {
            console.printError("PREHOOK: query: " + sess.getCmd().trim());
            console.printError("PREHOOK: type: " + sess.getCommandType());
        }

        printEntities(console, inputs, "PREHOOK: Input: ");
        printEntities(console, outputs, "PREHOOK: Output: ");
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
