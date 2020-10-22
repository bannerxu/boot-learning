package top.banner.service;

import org.springframework.stereotype.Service;
import top.banner.bean.UserVO;
import top.banner.entity.User;
import top.banner.repository.UserRepository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author XGL
 */
@Service
public class UserService {
    @Resource
    private UserRepository userRepository;

    /**
     * @return 全部用户
     */
    public List<User> all() {
        return userRepository.findAll();
    }

    public List<UserVO> groupBy() {
        List<UserVO> userVOS = userRepository.groupBy();
        return userVOS;
    }
}
