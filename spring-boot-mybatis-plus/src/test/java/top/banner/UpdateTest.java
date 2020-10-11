package top.banner;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import org.junit.Test;
import top.banner.entity.User;
import top.banner.mapper.UserMapper;

import javax.annotation.Resource;

/**
 * @author XGL
 */
public class UpdateTest extends BaseTest {
    @Resource
    private UserMapper userMapper;


    /**
     * UPDATE user SET name=?, age=? WHERE id=?
     */
    @Test
    public void updateById() {
        User user = new User();
        user.setId(1314382578609872898L);
        user.setName("李四");
        user.setAge(26);
        int count = userMapper.updateById(user);
        System.out.println("影响记录数：" + count);
    }

    /**
     * UPDATE user SET age=?, email=? WHERE (name = ? AND age = ?)
     */
    @Test
    public void updateByWrapper() {
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("name", "李艺伟").eq("age", 28);
        User user = new User();
        user.setEmail("2@qq.com");
        user.setAge(29);
        int count = userMapper.update(user, wrapper);
        System.out.println("影响记录数：" + count);
    }


    /**
     * UPDATE user SET age=?, email=? WHERE name=? AND (name = ? AND age = ?)
     */
    @Test
    public void updateByWrapper1() {
        User whereUser = new User();
        whereUser.setName("李艺伟");
        UpdateWrapper<User> wrapper = new UpdateWrapper<>(whereUser);
        wrapper.eq("name", "李艺伟").eq("age", 28);
        User user = new User();
        user.setEmail("2@qq.com");
        user.setAge(29);
        int count = userMapper.update(user, wrapper);
        System.out.println("影响记录数：" + count);
    }


    /**
     * UPDATE user SET age=? WHERE (name = ? AND age = ?)
     */
    @Test
    public void updateByWrapper2() {

        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("name", "李艺伟").eq("age", 28).set("age", 29);
        int count = userMapper.update(null, wrapper);
        System.out.println("影响记录数：" + count);
    }


    /**
     * UPDATE user SET age=? WHERE (name = ? AND age = ?)
     */
    @Test
    public void updateByWrapperLambda() {

        LambdaUpdateWrapper<User> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(User::getName, "李艺伟").eq(User::getAge, 29).set(User::getAge, 30);
        int count = userMapper.update(null, wrapper);
        System.out.println("影响记录数：" + count);
    }

    /**
     * UPDATE user SET age=? WHERE (name = ? AND age = ?)
     */
    @Test
    public void updateByWrapperLambdaChain() {

        LambdaUpdateChainWrapper<User> wrapper = new LambdaUpdateChainWrapper<>(userMapper);
        boolean update = wrapper.eq(User::getName, "李艺伟").eq(User::getAge, 29).set(User::getAge, 30).update();
        System.out.println("修改：" + update);
    }
}
