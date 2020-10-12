package top.banner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.Test;
import top.banner.entity.User;
import top.banner.mapper.UserMapper;

import javax.annotation.Resource;

/**
 * 自动填充
 *
 * @author XGL
 */
public class OptLockTest extends BaseTest {


    @Resource
    private UserMapper userMapper;


    /**
     * INSERT INTO user ( id, name, age, email, manager_id, create_time, update_time ) VALUES ( ?, ?, ?, ?, ?, ?, ? )
     */
    @Test
    public void update() {
        int version = 2;
        User user = new User();
        user.setEmail("lmc3@qq.com");
        user.setVersion(version);
        QueryWrapper<User> wrapper = Wrappers.query();
        wrapper.eq("name", "刘明超");

        //==>  Preparing: UPDATE user SET email=?, update_time=?, version=? WHERE deleted=0 AND (name = ? AND version = ?)
        //==> Parameters: lmc3@qq.com(String), 2020-10-12T09:26:08.720(LocalDateTime), 3(Integer), 刘明超(String), 2(Integer)
        //<==    Updates: 1
        int count = userMapper.update(user, wrapper);
        System.out.println("影响行数：" + count);

        int version2 = 3;
        User user1 = new User();
        user1.setEmail("lmc4@qq.com");
        user1.setVersion(version2);
        wrapper.eq("age", 25);


        //复用Wrapper后version条件失效，并混乱。
        //==>  Preparing: UPDATE user SET email=?, update_time=?, version=? WHERE deleted=0 AND (name = ? AND version = ? AND age = ? AND version = ?)
        //==> Parameters: lmc4@qq.com(String), 2020-10-12T09:26:09.131(LocalDateTime), 4(Integer), 刘明超(String), 2(Integer), 25(Integer), 3(Integer)
        //<==    Updates: 0
        int count2 = userMapper.update(user1, wrapper);
        System.out.println("影响行数：" + count2);
    }

    /**
     * UPDATE user SET age=?, update_time=?, version=? WHERE id=? AND version=? AND deleted=0
     */
    @Test
    public void updateById() {
        int version = 1;

        User user = new User();
        user.setId(1315322912604966913L);
        user.setAge(27);
        user.setVersion(version);
        int count = userMapper.updateById(user);
        System.out.println("影响行数：" + count);
    }
}
