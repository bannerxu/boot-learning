package top.banner;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.Test;
import top.banner.entity.User;
import top.banner.mapper.UserMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * 逻辑删除
 *
 * @author XGL
 */
public class LogicDeleteTest extends BaseTest {

    @Resource
    private UserMapper userMapper;


    /**
     * UPDATE user SET deleted=1 WHERE id=? AND deleted=0
     */
    @Test
    public void deleteById() {
        int count = userMapper.deleteById(1094592041087729666L);
        System.out.println("影响行数：" + count);
    }

    /**
     * SELECT id,name,age,email,manager_id,create_time,update_time,version,deleted FROM user WHERE deleted=0
     */
    @Test
    public void select() {
        List<User> list = userMapper.selectList(null);
        list.forEach(System.out::println);
    }

    /**
     * UPDATE user SET age=? WHERE id=? AND deleted=0
     */
    @Test
    public void updateById() {
        User user = new User();
        user.setId(1088248166370832385L);
        user.setAge(27);
//        user.setUpdateTime(LocalDateTime.now()); 设置了，就不自动填充了。getFieldValByName()
        int count = userMapper.updateById(user);
        System.out.println("影响行数：" + count);
    }


    /**
     * select * from user WHERE (age > ?)
     * 自定义xml是不支持逻辑删除，不会自动加上delete = 0
     * <p>
     * 解决方法：自己加上
     */
    @Test
    public void mySelectAll() {
        List<User> all = userMapper.selectAll(Wrappers.<User>lambdaQuery().gt(User::getAge, 20));
        System.out.println(all);
    }
}
