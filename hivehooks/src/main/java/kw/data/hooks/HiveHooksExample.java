package kw.data.hooks;

import org.apache.hadoop.hive.ql.hooks.ExecuteWithHookContext;
import org.apache.hadoop.hive.ql.hooks.HookContext;

/**
 * @author yangfei
 * @create 2020-06-02 10:02
 */
public class HiveHooksExample implements ExecuteWithHookContext {
    @Override
    public void run(HookContext hookContext) throws Exception {
        System.out.println(" this is a simple hive hook test ");

    }


}
