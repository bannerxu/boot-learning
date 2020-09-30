package top.banner.service;

import org.springframework.stereotype.Service;
import top.banner.mapper.UserMapper;
import top.banner.model.User;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author XGL
 */
@Service
public class UserService {
    @Resource
    private UserMapper userMapper;


    public User findOne(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public List<User> findAll() {
        return userMapper.selectAll();
    }
}
