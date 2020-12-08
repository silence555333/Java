package kw.kd.dss.config;

/**
 * @author yangfei
 * @version 1.0
 * @Description TODO
 * @create 2020-11-30 20:26
 */
public class EnvDSSServerConstant {
    public static final String DSS_DEV_HDFS_URL = "hdfs://m5.server:8020";
    public static final String DWS_PRO_HDFS_URL_FORMAT = "hdfs://%s:8020";
    public static final String DWS_HDFS_LOCAL_TMP_DIR="/tmp/dss/copyhdfstolocal/jsondir/";
    public static final String MOVEING_MESSAGE = "项目正在发布,请勿重复请求";
    public static final String MOVEING_FINISH = "项目发布完成";
    public static final String MOVEING_FAIL = "项目发布失败，请稍后重试或联系后台开发人员";
}
