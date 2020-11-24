package kw.kd.dss.repository;


import kw.kd.dss.entity.User;
import kw.kd.dss.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Auther: yukong
 * @Date: 2018/8/14 16:34
 * @Description:
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

//    @Test
//    public void save() {
//        User user = new User();
//        user.setUsername("master");
//        user.setPassword("master");
//        user.setEmail("silence@sina.com");
//        user.setMobile("1234567876");
//        Assert.assertEquals(1,userMapper.save(user));
//    }

//    @Test
//    public void update() {
//        User user = new User();
//        user.setId(8L);
//        user.setPassword("newpassword");
//        // 返回插入的记录数 ，期望是1条 如果实际不是一条则抛出异常
//        Assert.assertEquals(1,userMapper.update(user));
//    }

    @Test
    public void selectById() {
        User user = userMapper.selectById("03d13e10dc1f435088c1f6a2e3627558");
        System.out.println("id:" + user.getId());
        System.out.println("name:" + user.getUsername());
        System.out.println("password:" + user.getPassword());
    }

//    @Test
//    public void deleteById() {
//        Assert.assertEquals(1,userMapper.deleteById("esss"));
//    }

    @Test
    public void selectAll() {
        List<User> users= userMapper.selectAll();
        users.forEach(user -> {
            System.out.println("id:" + user.getId());
            System.out.println("name:" + user.getUsername());
            System.out.println("password:" + user.getPassword());
        });
    }

//    @Test
//    public void testTransactional() {
//       userService.testTransactional();
//    }


}