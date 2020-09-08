package kw.data.client;
import org.apache.hadoop.hive.ql.hooks.Hook;
import org.apache.hadoop.hive.ql.hooks.LineageInfo;
import org.apache.hadoop.hive.ql.hooks.ReadEntity;
import org.apache.hadoop.hive.ql.hooks.WriteEntity;
import org.apache.hadoop.hive.ql.session.SessionState;
import java.util.Set;
import org.apache.hadoop.security.UserGroupInformation;
/**
 * @author yangfei
 * @create 2020-07-08 11:34
 */
public interface PostExecute extends Hook {
    /**
     * The run command that is called just before the execution of the query.
     *
     * @param sess
     *          The session state.
     * @param inputs
     *          The set of input tables and partitions.
     * @param outputs
     *          The set of output tables, partitions, local and hdfs directories.
     * @param lInfo
     *           The column level lineage information.
     * @param ugi
     *          The user group security information.
     */
    @Deprecated
    void run(SessionState sess, Set<ReadEntity> inputs,
             Set<WriteEntity> outputs, LineageInfo lInfo,
             UserGroupInformation ugi) throws Exception;
}
