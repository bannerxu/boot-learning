package top.banner.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.banner.entity.User;
import top.banner.mapper.UserMapper;
import top.banner.service.UserService;

/**
 * @author XGL
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
