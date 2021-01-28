package top.banner.shiro.shiro;


import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import top.banner.shiro.dao.ManagerRepository;
import top.banner.shiro.dao.SysUserRepository;
import top.banner.shiro.entity.Manager;
import top.banner.shiro.entity.SysUser;
import top.banner.shiro.service.RedisKeyPrefix;

import javax.annotation.Resource;

/**
 * @author xgl
 */
@Component
public class CmsRealm extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(AuthorizingRealm.class);
    @Resource
    private ManagerRepository managerRepository;
    @Resource
    private SysUserRepository sysUserRepository;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String getName() {
        return "redisRealm";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token != null && Token.class.isAssignableFrom(token.getClass());
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        final Token token = (Token) authenticationToken;
        final String tokenString = token.getToken();

        ValueOperations<String, String> redis = stringRedisTemplate.opsForValue();

        if (tokenString.startsWith("sysUser_")) {
            String sysUserId = redis.get(RedisKeyPrefix.buildTokenToSysUserId(tokenString));
            if (!StringUtils.isEmpty(sysUserId)) {
                return new SimpleAuthenticationInfo(sysUserId, tokenString, "sysUser");
            }
        }
        final String userId = redis.get(RedisKeyPrefix.buildTokenToManagerId(tokenString));
        if (!StringUtils.isEmpty(userId)) {
            return new SimpleAuthenticationInfo(userId, tokenString, "manager");
        }
        return null;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        final String id = (String) principals.getPrimaryPrincipal();
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        if (principals.getRealmNames().contains("sysUser")) {
            SysUser sysUser = sysUserRepository.findById(Integer.valueOf(id)).orElseThrow(() -> new RuntimeException("用户不存在"));
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            logger.info("--------------------name：{} ; account：{} ; role：{}", sysUser.getAccount(),
                    sysUser.getAccount(), "groupOwner");
            info.addRole("groupOwner");
            return info;

        } else {

            Manager manager = managerRepository.findById(Integer.valueOf(id)).orElseThrow(() -> new RuntimeException("用户不存在"));
            assert manager != null;
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            logger.info("--------------------name：{} ; account：{} ; role：{}", manager.getAccount(),
                    manager.getAccount(), manager.getRole());
            info.addStringPermissions(manager.getRole().getAuthority().asList());
            info.addRole(manager.getRole().getName());
            return info;

        }
    }


}
