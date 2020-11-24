
import org.apache.hadoop.hive.ql.hooks.LineageLogger;
import org.apache.hadoop.hive.ql.parse.ParseException;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.tools.LineageInfo;

import java.io.IOException;

/**
 * @author yangfei
 * @version 1.0
 * @Description TODO
 * @create 2020-11-12 17:06
 */
public class LIneageLoggerTest {
    public static void main(String[] args) throws ParseException, SemanticException, IOException {
        String query = "INSERT OVERWRITE TABLE cxy7_dw.tmp_zone_info PARTITION (dt='20171109') SELECT z.zoneid AS zone_id,z.zonename AS zone_name, c.cityid AS city_id, c.cityname AS city_name FROM dict_zoneinfo z LEFT JOIN dict_cityinfo c ON z.cityid = c.cityid AND z.dt='20171109' AND c.dt='20171109' WHERE z.dt='20171109' AND c.dt='20171109'";
        LineageInfo.main(new String[] { query });

    }
}
