package top.banner;

import org.junit.Test;
import org.springframework.data.jpa.domain.Specification;
import top.banner.entity.User;
import top.banner.repository.UserRepository;
import top.banner.utils.CommonSpecUtil;

import javax.annotation.Resource;

public class SpecTest extends BaseTest {
    @Resource
    private CommonSpecUtil<User> specUtil;
    @Resource
    private UserRepository userRepository;


    /**
     * select *
     * from user u
     * where u.gender = 0
     * and (u.username like ?)
     */
    @Test
    public void test() {
        Specification<User> and = Specification.<User>where((root, query, cb) -> cb.like(root.get("username"), "%张三%"))
                .and((root, cq, cb) -> cb.equal(root.get("gender"), 0));
        userRepository.findAll(and);
    }


}
