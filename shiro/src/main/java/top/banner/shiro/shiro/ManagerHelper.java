package top.banner.shiro.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;

public class ManagerHelper {
    public static Integer getManagerId() {
        final Subject subject = SecurityUtils.getSubject();
        final Object manager = subject.getPrincipal();
        if (manager == null) {
            throw new AuthenticationException();
        }
        return Integer.parseInt(manager.toString());
    }
}