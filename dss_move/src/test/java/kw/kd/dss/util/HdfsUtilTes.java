package kw.kd.dss.util;

/**
 * @author yangfei
 * @version 1.0
 * @Description TODO
 * @create 2020-12-08 17:44
 */

import java.io.*;
import java.net.URI;

import kw.kd.dss.config.EnvDSSServerConstant;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;
import org.junit.Test;

public class HdfsUtilTes {
    private static String SOURCE_PATH = "/Users/wangyue/Desktop/soft-jar/1585843200000";
    private static String DEST_PATH = "/tmp/test_yf/1585843200000";
    private static String MASTER_URI = EnvDSSServerConstant.DSS_DEV_HDFS_URL;
    private   HdfsFileUtil hdfsFileUtil=new HdfsFileUtil();

    /**
     * 测试下载文件
     * @throws Exception
     */
    @Test
    public void testDownload() throws Exception {
        Configuration conf = new Configuration();
        hdfsFileUtil.download(conf,MASTER_URI,MASTER_URI+DEST_PATH,SOURCE_PATH);
    }

    /**
     * 复制文件到远程文件系统，也可以看做是上传
     * @param conf
     * @param uri
     * @param local
     * @param remote
     * @throws IOException
     */
    public static void copyFile(Configuration conf, String uri, String local, String remote) throws IOException {
        FileSystem fs = FileSystem.get(URI.create(uri), conf);
        fs.copyFromLocalFile(new Path(local), new Path(remote));
        System.out.println("copy from: " + local + " to " + remote);
        fs.close();
    }

//    @Test
    public void testCopyFile() throws IOException {
        // 使用命令：hadoop fs -ls  /hbase/upload， 可以查看复制到/hbase/upload目录下的文件
        copyFile(new Configuration(), MASTER_URI, SOURCE_PATH, "/hbase/upload/settings01.jar");
    }

}
