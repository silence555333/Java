package com.data.lineage.client;

import com.data.lineage.parse.LineParser;
import com.data.lineage.util.PropertyFileUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.ql.hooks.ExecuteWithHookContext;
import org.apache.hadoop.hive.ql.hooks.HookContext;

/**
 * @author yangfei
 * @create 2020-07-07 14:34
 * 开始解析的主类
 */
public class HiveLineage  implements ExecuteWithHookContext {
    static  final private Log LOG= LogFactory.getLog(HiveLineage.class);
    LineParser parse = null;

    protected void setUp() throws Exception {
        PropertyFileUtil.init(); //设置环境变量
        parse = new LineParser();
    }

    @Override
    public void run(HookContext hookContext) throws Exception {
        final HookContext.HookType  hookType=hookContext.getHookType();
        final String operationName=hookContext.getOperationName();

        if (HookContext.HookType.POST_EXEC_HOOK != hookContext.getHookType()) {
            LOG.info("Hive hook " + hookType + " , operation name " + operationName);
            return;
        }
    }
}
