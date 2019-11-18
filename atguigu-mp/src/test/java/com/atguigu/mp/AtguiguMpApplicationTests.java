package com.atguigu.mp;

import com.atguigu.mp.entity.User;
import com.atguigu.mp.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AtguiguMpApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println(6666);
    }

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectList() {
        System.out.println(("----- selectAll method test ------"));
        //UserMapper 中的 selectList() 方法的参数为 MP 内置的条件封装器 Wrapper
        //所以不填写就是无任何条件
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    /**
     *插入测试
     */
    @Test
    public void testInsert() {
        User user = new User();
        user.setAge(18);
        user.setEmail("2313212421@qq.com");
        user.setName("奥斯卡");
        int result = userMapper.insert(user);
        System.err.println(user);

    }

    /****
     * 更新测试
     */
    @Test
    public void testUpdate() {
        User user = new User();
        user.setId(1l);
        user.setAge(18);
        userMapper.updateById(user);
        System.err.println(user);
    }
}
