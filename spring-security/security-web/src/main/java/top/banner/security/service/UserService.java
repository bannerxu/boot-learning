package top.banner.security.service;

import org.springframework.stereotype.Service;
import top.banner.security.entity.User;
import top.banner.security.repository.UserRepository;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {
    @Resource
    private UserRepository userRepository;

    public List<User> list() {
        return userRepository.findAll();
    }


}
