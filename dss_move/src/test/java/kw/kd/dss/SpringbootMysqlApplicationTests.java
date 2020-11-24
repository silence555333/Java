package kw.kd.dss;

import com.webank.wedatasphere.dss.common.entity.project.DWSProject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author yangfei
 * @version 1.0
 * @Description TODO
 * @create 2020-11-24 11:55
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootMysqlApplicationTests {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Test
    public void contextLoads() {
        String sql = "select * from dss_project";
        List<DWSProject> users = jdbcTemplate.query(sql, new RowMapper<DWSProject>() {
            @Override
            public DWSProject mapRow(ResultSet resultSet, int i) throws SQLException {
                DWSProject dss = new DWSProject();
                dss.setId(resultSet.getLong("id"));
//                user.setId(resultSet.getString("id"));
//                user.setName(resultSet.getString("name"));
//                user.setPassword(resultSet.getString("password"));

                return dss;
            }
        });
        System.out.println("查询成功：" + users);
    }
}
