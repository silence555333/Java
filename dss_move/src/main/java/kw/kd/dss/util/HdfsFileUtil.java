package kw.kd.dss.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yangfei
 * @version 1.0
 * @Description TODO
 * @create 2020-12-08 17:36
 */
public class HdfsFileUtil {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 上传文件
     * @param conf
     * @param local
     * @param remote
     * @throws IOException
     */
    public  void copyFile(Configuration conf , String uri , String local, String remote) throws IOException {
        FileSystem fs = FileSystem.get(URI.create(uri), conf);
        fs.copyFromLocalFile(new Path(local), new Path(remote));
        logger.info("copy from: " + local + " to " + remote);
        fs.close();
    }

    /**
     * 获取hdfs上文件流
     * @param conf
     * @param uri
     * @param local
     * @param remote
     * @throws IOException
     */
    public   void getFileStream(Configuration conf , String uri , String local, String remote) throws IOException{
        FileSystem fs = FileSystem.get(URI.create(uri), conf);
        Path path= new Path(remote);
        FSDataInputStream in = fs.open(path);//获取文件流
        FileOutputStream fos = new FileOutputStream(local);//输出流
        int ch = 0;
        while((ch=in.read()) != -1){
            fos.write(ch);
        }
        in.close();
        fos.close();
    }

    /**
     * 创建文件夹
     * @param conf
     * @param uri
     * @param remoteFile
     * @throws IOException
     */
    public  void markDir(Configuration conf , String uri , String remoteFile ) throws IOException{
        FileSystem fs = FileSystem.get(URI.create(uri), conf);
        Path path = new Path(remoteFile);

        fs.mkdirs(path);
        logger.info("创建文件夹"+remoteFile);

    }
    /**
     * 查看文件
     * @param conf
     * @param uri
     * @param remoteFile
     * @throws IOException
     */
    public  void cat(Configuration conf , String uri ,String remoteFile) throws IOException {
        Path path = new Path(remoteFile);
        FileSystem fs = FileSystem.get(URI.create(uri), conf);
        FSDataInputStream fsdis = null;
        logger.info("cat: " + remoteFile);
        try {
            fsdis = fs.open(path);
            IOUtils.copyBytes(fsdis, System.out, 4096, false);
        } finally {
            IOUtils.closeStream(fsdis);
            fs.close();
        }
    }
    /**
     * 下载 hdfs上的文件
     * @param conf
     * @param uri
     * @param remote
     * @param local
     * @throws IOException
     */
    public  void download(Configuration conf , String uri ,String remote, String local) throws IOException {
        //如果本地文件存在则删除
        if(FileUtil.exist(local)){FileUtil.delFile(local);}
        Path path = new Path(remote);
        FileSystem fs = FileSystem.get(URI.create(uri), conf);
        fs.copyToLocalFile(path, new Path(local));
        logger.info("download: from" + remote + " to " + local);
        fs.close();
    }
    /**
     * 删除文件或者文件夹
     * @param conf
     * @param uri
     * @param filePath
     * @throws IOException
     */
    public  void delete(Configuration conf , String uri,String filePath) throws IOException {
        Path path = new Path(filePath);
        FileSystem fs = FileSystem.get(URI.create(uri), conf);
        fs.deleteOnExit(path);
        logger.info("Delete: " + filePath);
        fs.close();
    }
    /**
     * 查看目录下面的文件
     * @param conf
     * @param uri
     * @param folder
     * @throws IOException
     */
    public   void ls(Configuration conf , String uri , String folder) throws IOException {
        Path path = new Path(folder);
        FileSystem fs = FileSystem.get(URI.create(uri), conf);
        FileStatus[] list = fs.listStatus(path);
        System.out.println("ls: " + folder);
        for (FileStatus f : list) {
            logger.info(String.format("name: %s, folder: %s, size: %d\n", f.getPath(),f.isDirectory() , f.getLen()));
        }
        fs.close();
    }


}
