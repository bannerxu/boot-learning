package top.banner.security.provider;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * AuthenticationTokenParser
 *
 * @author hanfeng
 */
public interface AuthenticationTokenParser {
    AbstractAuthenticationToken parse(String authenticationTokenStr);
}
