package top.banner;

import org.junit.Test;
import top.banner.repository.UserRepository;

import javax.annotation.Resource;

/**
 * @author XGL
 */
public class UserTest extends BaseTest {
    @Resource
    private UserRepository userRepository;

    @Test
    public void all() {
        userRepository.findAll().forEach(System.out::println);
    }

    @Test
    public void customSQL() {
        userRepository.groupBy().forEach(System.out::println);
    }
}
