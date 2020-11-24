package kw.kd.dss.service;


import kw.kd.dss.entity.User;
import kw.kd.dss.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: xiongping22369
 * @Date: 2018/8/17 08:55
 * @Description:
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void testTransactional() {
        User user = new User();
        user.setUsername("Transactional");
        user.setPassword("Transactional");
        user.setEmail("silence@sina.com");
        user.setMobile("1234565432");
        userMapper.save(user);
        User user1 = new User();
        user1.setId("qwerasdf");
        user1.setPassword("Transactional");
        // 返回插入的记录数 ，期望是1条 如果实际不是一条则抛出异常
        System.out.println(userMapper.update(user1));
        throw new RuntimeException();
    }


}
