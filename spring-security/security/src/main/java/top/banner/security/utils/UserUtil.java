package top.banner.security.utils;

import javax.servlet.http.HttpServletRequest;

public class UserUtil {
    public static String loginUsername(HttpServletRequest request) {
        return request.getParameter("username");
    }
}
