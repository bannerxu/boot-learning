package top.banner;

import org.junit.Test;
import top.banner.entity.User;
import top.banner.mapper.UserMapper;

import javax.annotation.Resource;

/**
 * 自动填充
 *
 * @author XGL
 */
public class FillTest extends BaseTest {


    @Resource
    private UserMapper userMapper;


    /**
     * INSERT INTO user ( id, name, age, email, manager_id, create_time, update_time ) VALUES ( ?, ?, ?, ?, ?, ?, ? )
     */
    @Test
    public void install() {
        User user = new User();
        user.setName("刘明超");
        user.setAge(31);
        user.setEmail("lmc@qq.com");
        user.setManagerId(1088248166370832385L);
        int count = userMapper.insert(user);
        System.out.println("影响行数：" + count);

    }

    /**
     * UPDATE user SET age=?, update_time=? WHERE id=? AND deleted=0
     */
    @Test
    public void update() {
        User user = new User();
        user.setId(1088248166370832385L);
        user.setAge(27);
        int count = userMapper.updateById(user);
        System.out.println("影响行数：" + count);
    }
}
