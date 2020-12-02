package kw.kd.dss.util;

import org.apache.hadoop.fs.Path;
import org.junit.Test;

/**
 * @author yangfei
 * @version 1.0
 * @Description TODO
 * @create 2020-11-30 17:17
 */

public class DistcpTest {

    @Test
    public void discpFile(){
        String srcfile="hdfs://prd-hadoop-02.xbox:8020/tmp/testyf/company_inv/UPSERT/1585843200000";
        String tarfile="hdfs://m5.server:8020/tmp/test_yf";
        DistcpHdfsUtil distcpHdfsUtil=new DistcpHdfsUtil();
        distcpHdfsUtil.distCopy(new Path(srcfile), new Path(tarfile));
    }

//    @Test
//    public void copyhdfstolocal(){
//        String tarfile="hdfs://m5.server:8020/tmp/test_yf/1585843200000";
//        String local="/Users/wangyue/Desktop/soft-jar/1585843200000";
//
//        HDFSUtil.downloadHDFS2Localfile(tarfile,local);
//
//    }
}
