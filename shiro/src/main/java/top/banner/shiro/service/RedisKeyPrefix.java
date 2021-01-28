package top.banner.shiro.service;

/**
 * @author xgl
 */
public class RedisKeyPrefix {


    public static String buildTokenToManagerId(String token) {
        return "manager.token.uid:" + token;
    }

    public static String buildManagerIdToToken(Integer managerId) {
        return "manager.uid.token:" + managerId;
    }


    public static String buildTokenToSysUserId(String token) {
        return "sysUser.token.id:" + token;
    }


    public static String buildSysUserIdToToken(Integer sysUserId) {
        return "sysUser.id.token:" + sysUserId;
    }


}

