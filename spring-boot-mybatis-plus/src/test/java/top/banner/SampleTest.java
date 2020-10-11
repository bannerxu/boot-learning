package top.banner;

import org.junit.Test;
import top.banner.entity.User;
import top.banner.mapper.UserMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author XGL
 */
public class SampleTest extends BaseTest {

    @Resource
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
//        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }

    @Test
    public void install() {
        User user = new User();
        user.setAge(10);
        user.setEmail("1@qq.com");
        user.setName("张三");
        user.setRemake("备注");
        userMapper.insert(user);

    }
}