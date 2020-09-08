package kw.data.util;
import java.io.OutputStream;
import java.net.URI;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author yangfei
 * @create 2020-06-24 17:36
 */
public class HDFSUtil {
    public static void  writeFile(String str){
        String activenode=ZookeeperClient.getActiveNode();
        String hdfsUrl = "hdfs://"+activenode+":8020";
        String hdfs_path = hdfsUrl + "/tmp/hive/lineage/lineage.log";
        Configuration conf = new Configuration();

        conf.setBoolean("dfs.support.append", true);
        FileSystem fs = null;
        try {
            fs = FileSystem.get(URI.create(hdfs_path), conf);
            OutputStream output = fs.append(new Path(hdfs_path));
            ObjectMapper objectMapper = new ObjectMapper();
            output.write(str.getBytes("UTF-8"));
            output.write("\n".getBytes("UTF-8"));//换行
            fs.close();
            output.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
