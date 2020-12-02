package kw.kd.dss.util;
import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.hadoop.hdfs.server.namenode.ha.proto.HAZKInfoProtos;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

import static org.apache.zookeeper.Watcher.Event.EventType.NodeDataChanged;

/**
 * <Description> 通过zk获取HA 中的存活节点<br>
 *
 * @author yangfei<br>
 * @taskId: <br>
 * @version 1.0<br>
 * @createDate 2020/03/23 9:29 <br>
 */
public class ZookeeperClient {

    private static ZooKeeper zk;

    private static Stat stat = new Stat();

    private static String HA_ACTIVE_NODE = "";

    public  static String  HDFS_HA_ZK_ADDRESS= "prd-hadoop-03.xbox:2181,prd-hadoop-04.xbox:2181,prd-hadoop-05.xbox:2181";

    public  static String HDFS_HA_ZK_NODE= "/hadoop-ha/knowlegene/ActiveStandbyElectorLock";

    static {
        try {
            zk = new ZooKeeper(HDFS_HA_ZK_ADDRESS, 50000, new Watcher() {

                public void process(WatchedEvent event) {
                    if (event.getType() == NodeDataChanged) {
                        update();
                        System.out.println("HA Address changed......");
                    }

                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        update();
    }

    private static void update() {
        try {
            byte[] bytes = zk.getData(HDFS_HA_ZK_NODE, true, stat);
            HAZKInfoProtos.ActiveNodeInfo nodeInfo = HAZKInfoProtos.ActiveNodeInfo.parseFrom(bytes);
            HA_ACTIVE_NODE = nodeInfo.getHostname();
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }


    public static String getActiveNode() {
        return HA_ACTIVE_NODE;
    }

    public static void main(String[] args) {
        System.out.println(getActiveNode());
    }

}
