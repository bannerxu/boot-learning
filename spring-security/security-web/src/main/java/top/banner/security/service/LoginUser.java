package top.banner.security.service;

import lombok.Data;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;
import top.banner.security.service.vo.UserVO;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 扩展用户信息
 *
 * @author songyinyin
 * @date 2020/3/14 下午 05:29
 */
@Data
public class LoginUser implements UserDetails, CredentialsContainer, Serializable {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    /**
     * 用户
     */
    private UserVO user;
    /**
     * 登录ip
     */
    private String loginIp;
    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    public LoginUser() {
    }

    public LoginUser(UserVO user, String loginIp, LocalDateTime loginTime) {
        this.user = user;
        this.loginIp = loginIp;
        this.loginTime = loginTime;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * 账户是否未过期，过期无法验证
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 指定用户是否解锁，锁定的用户无法进行身份验证
     * <p>
     * 密码锁定
     * </p>
     */
    @Override
    public boolean isAccountNonLocked() {
        return !user.getLocked();
    }

    /**
     * 指示是否已过期的用户的凭据(密码)，过期的凭据防止认证
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 用户是否被启用或禁用。禁用的用户无法进行身份验证。
     */
    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }

    /**
     * 认证完成后，擦除密码
     */
    @Override
    public void eraseCredentials() {
        user.setPassword("***");
    }
}