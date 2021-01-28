package top.banner.shiro.shiro;


import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author xgl
 */
public class Token implements AuthenticationToken {

    private String token;

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return getToken();
    }

    @Override
    public Object getCredentials() {
        return getToken();
    }
}
