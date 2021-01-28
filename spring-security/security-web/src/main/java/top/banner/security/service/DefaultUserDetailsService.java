package top.banner.security.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import top.banner.security.entity.User;
import top.banner.security.repository.UserRepository;
import top.banner.security.service.vo.UserVO;
import top.banner.security.utils.BeanUtils;

import java.time.LocalDateTime;

@Slf4j
@Component
public class DefaultUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isBlank(username)) {
            log.info("登录用户：{} 不存在", username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        }
        // 查出密码
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.info("登录用户：{} 不存在", username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        }


        UserVO userVO = new UserVO();
        BeanUtils.copyNonNullProperties(user, userVO);

        return new LoginUser(userVO, "127.0.0.1", LocalDateTime.now());
    }
}