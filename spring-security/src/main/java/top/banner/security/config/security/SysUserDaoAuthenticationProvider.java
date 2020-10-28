package top.banner.security.config.security;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author XGL
 */
@Component
public class SysUserDaoAuthenticationProvider extends DaoAuthenticationProvider {
    @Override
    public boolean supports(Class<?> authentication) {
        return SysUserAuthenticationToken.class.equals(authentication);
    }

//
//    @Override
//    protected void doAfterPropertiesSet() {
//        setPasswordEncoder(passwordEncoder)
//    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(SysUserAuthenticationToken.class, authentication,
                messages.getMessage(
                        "SysUserDaoAuthenticationProvider.onlySupports",
                        "Only SysUserAuthenticationToken is supported"));
        return null;
    }
}
