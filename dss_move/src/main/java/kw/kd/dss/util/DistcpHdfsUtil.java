package kw.kd.dss.util;

import kw.kd.dss.config.CommonConfigInfo;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.tools.DistCp;
import org.apache.hadoop.tools.DistCpOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.PrivilegedExceptionAction;

/**
 * @author yangfei
 * @version 1.0
 * @Description TODO
 * @create 2020-11-30 16:51
 */

public class DistcpHdfsUtil {

    private static final Logger logger = LoggerFactory.getLogger(DistcpHdfsUtil.class);

    public void distCopy(Path sourceFile, Path destFile) {
        UserGroupInformation ugi =
                UserGroupInformation.createRemoteUser(CommonConfigInfo.hdfs_pro_user);  //设置成功
        try {
            ugi.doAs( new PrivilegedExceptionAction<Void>() {
                          @Override
                          public Void run() throws Exception {
                              Configuration conf = new Configuration();
                              DistCp distCp = new DistCp(conf, new DistCpOptions(sourceFile, destFile));
                              distCp.run(new String[] { sourceFile.toString(), destFile.toString() });
                              return null;
                          }
                      });
        } catch (Exception e) {
            logger.error("Couldn't execute distCp from {} to {}", sourceFile.toString(), destFile.toString());
            e.printStackTrace();
        }
    }
}


