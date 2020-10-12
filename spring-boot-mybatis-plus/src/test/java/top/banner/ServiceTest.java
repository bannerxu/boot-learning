package top.banner;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.Test;
import top.banner.entity.User;
import top.banner.service.UserService;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author XGL
 */
public class ServiceTest extends BaseTest {

    @Resource
    private UserService userService;

    @Test
    public void getOne() {
//        User user = userService.getOne(Wrappers.<User>lambdaQuery().gt(User::getAge, 25)); //只能查询一条数据，要不然会报TooManyResultsException
        User user = userService.getOne(Wrappers.<User>lambdaQuery().gt(User::getAge, 25), false);  //只会返回第一条
        System.out.println(user);
    }


    @Test
    public void batch() {

        User user = new User();
        user.setName("徐丽3");
        user.setAge(28);

        User user1 = new User();
        user1.setId(1094592041087729668L);
        user1.setName("徐丽2");
        user1.setAge(30);

        List<User> list = Arrays.asList(user, user1);
//        boolean b = userService.saveBatch(list); //INSERT INTO user ( name, age ) VALUES ( ?, ? )

        /*
        INSERT INTO user ( name, age ) VALUES ( ?, ? )
        SELECT id,name,age,email,manager_id,create_time FROM user WHERE id=?
        UPDATE user SET name=?, age=? WHERE id=?
         */
        boolean b = userService.saveOrUpdateBatch(list);
        System.out.println(b);

    }

    /**
     * 链式查询
     * SELECT id,name,age,email,manager_id,create_time FROM user WHERE (age > ? AND name LIKE ?)
     */
    @Test
    public void chain() {
        List<User> list = userService.lambdaQuery().gt(User::getAge, 25).like(User::getName, "雨").list();
        System.out.println(list);
    }


    /**
     * 链式修改
     * <p>
     * UPDATE user SET age=? WHERE (age = ?)
     */
    @Test
    public void chain1() {
        boolean update = userService.lambdaUpdate().eq(User::getAge, 25).set(User::getAge, 26).update();
        System.out.println(update);
    }

    /**
     * 可以进行删除
     * <p>
     * DELETE FROM user WHERE (age = ?)
     */
    @Test
    public void chain2() {
        boolean update = userService.lambdaUpdate().eq(User::getAge, 25).remove();
        System.out.println(update);
    }
}
