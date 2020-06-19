package kw.data.hooks;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.ql.hooks.ExecuteWithHookContext;
import org.apache.hadoop.hive.ql.hooks.HookContext;
import org.apache.hadoop.hive.metastore.api.Database;
import org.apache.hadoop.hive.ql.QueryPlan;
import org.apache.hadoop.hive.ql.hooks.*;
import org.apache.hadoop.hive.ql.plan.HiveOperation;

import org.codehaus.jackson.map.ObjectMapper;


import java.util.HashSet;
import java.util.Set;
/**
 * @author yangfei
 * @create 2020-06-16 19:36
 */
public class MyHooks implements ExecuteWithHookContext {

    static  final private Log logger= LogFactory.getLog(MyHooks.class);

    private static final HashSet<String> OPERATION_NAMES = new HashSet<>();

    static {
        OPERATION_NAMES.add(HiveOperation.CREATETABLE.getOperationName());
        OPERATION_NAMES.add(HiveOperation.ALTERDATABASE.getOperationName());
        OPERATION_NAMES.add(HiveOperation.ALTERDATABASE_OWNER.getOperationName());
        OPERATION_NAMES.add(HiveOperation.ALTERTABLE_ADDCOLS.getOperationName());
        OPERATION_NAMES.add(HiveOperation.ALTERTABLE_LOCATION.getOperationName());
        OPERATION_NAMES.add(HiveOperation.ALTERTABLE_PROPERTIES.getOperationName());
        OPERATION_NAMES.add(HiveOperation.ALTERTABLE_RENAME.getOperationName());
        OPERATION_NAMES.add(HiveOperation.ALTERTABLE_RENAMECOL.getOperationName());
        OPERATION_NAMES.add(HiveOperation.ALTERTABLE_REPLACECOLS.getOperationName());
        OPERATION_NAMES.add(HiveOperation.CREATEDATABASE.getOperationName());
        OPERATION_NAMES.add(HiveOperation.DROPDATABASE.getOperationName());
        OPERATION_NAMES.add(HiveOperation.DROPTABLE.getOperationName());
    }

    @Override
    public void run(HookContext hookContext) throws Exception {
        assert (hookContext.getHookType() == HookContext.HookType.POST_EXEC_HOOK);

        QueryPlan plan = hookContext.getQueryPlan();

        String operationName = plan.getOperationName();
        logWithHeader("Query executed: " + plan.getQueryString());
        logWithHeader("Operation: " + operationName);
        if (OPERATION_NAMES.contains(operationName)
                && !plan.isExplain()) {
            logWithHeader("Monitored Operation");
            Set<ReadEntity> inputs = hookContext.getInputs();
            Set<WriteEntity> outputs = hookContext.getOutputs();

            for (Entity entity : inputs) {
                logWithHeader("Hook metadata input value: " +  toJson(entity));
            }

            for (Entity entity : outputs) {
                logWithHeader("Hook metadata output value: " +  toJson(entity));
            }

        } else {
            logWithHeader("Non-monitored Operation, ignoring hook");
        }
    }

    private static String toJson(Entity entity) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        switch (entity.getType()) {
            case DATABASE:
                Database db = entity.getDatabase();
                return mapper.writeValueAsString(db);
            case TABLE:
                return mapper.writeValueAsString(entity.getTable().getTTable());
        }
        return null;
    }

    private void logWithHeader(Object obj){
        logger.info("[CustomHook][Thread: "+Thread.currentThread().getName()+"] | " + obj);
    }
}
