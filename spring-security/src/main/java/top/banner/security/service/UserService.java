package top.banner.security.service;

import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import top.banner.security.model.User;

public interface UserService extends AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    User login(String username, String password);

    String readToken(User user);
}
