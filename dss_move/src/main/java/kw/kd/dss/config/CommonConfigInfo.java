package kw.kd.dss.config;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
/**
 * @author yangfei
 * @version 1.0
 * @Description TODO
 * @create 2020-12-01 19:14
 */
public class CommonConfigInfo {
    private static final Config baseConfig = ConfigFactory.load();
    public static final String hdfs_pro_user = baseConfig.getString("product.dssuser.user");
}
