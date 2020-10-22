package top.banner;

import org.junit.Test;
import top.banner.service.UserService;

import javax.annotation.Resource;

/**
 * @author XGL
 */
public class UserTest extends BaseTest {
    @Resource
    private UserService userService;

    @Test
    public void all() {
        userService.all().forEach(System.out::println);
    }

    @Test
    public void customSQL() {
        userService.groupBy().forEach(System.out::println);
    }
}
