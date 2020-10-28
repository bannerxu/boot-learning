package top.banner.security.service.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.banner.security.bean.Who;
import top.banner.security.model.User;
import top.banner.security.model.UserToken;
import top.banner.security.repository.UserRepository;
import top.banner.security.repository.UserTokenRepository;
import top.banner.security.service.UserService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * @author XGL
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserRepository userRepository;
    @Resource
    private UserTokenRepository userTokenRepository;

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
        if (token.getCredentials() == null)
            throw new UsernameNotFoundException("用户不存在");
        String[] x = token.getCredentials().toString().split("\\+");
        User u = findOne(Long.parseLong(x[0]));

        return new Who() {
            @Override
            public Long getUserId() {
                return u.getId();
            }

            @Override
            public User getUserDetail() {
                return u;
            }

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return new ArrayList<>();
            }

            @Override
            public String getPassword() {
                return null;
            }

            @Override
            public String getUsername() {
                return "User#" + u.getId();
            }

            /**
             * @return 账户是否未过期
             */
            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            /**
             * @return 账户是否未被锁定
             */
            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            /**
             * @return 凭证未过期
             */
            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            /**
             * @return 是否已启用
             */
            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }

    public User findOne(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
    }

    @Override
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        if (!password.equals(user.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        newToken(user);
        return user;
    }

    @Override
    public String readToken(User user) {
        return userTokenRepository.findTop1ByUserIdAndExpiredFalseOrderByCreateTimeDesc(user.getId()).getId();
    }

    @Transactional
    public void newToken(User user) {
        userTokenRepository.save(new UserToken(user.getId() + "+" + UUID.randomUUID().toString().replace("-", ""), user.getId()));
    }


}
