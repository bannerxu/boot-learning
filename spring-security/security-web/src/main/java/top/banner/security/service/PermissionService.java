package top.banner.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("ps")
public class PermissionService {
    public boolean permission(String permission) {
//        LoginUser loginUser = UserUtil.loginUser();
//        for (String userPermission : loginUser.getUser().getPermissions()) {
//            if (permission.matches(userPermission)) {
//                return true;
//            }
//        }
//        if (log.isDebugEnabled()) {
//            log.debug("用户userId={}, userName={} 权限不足以访问[{}], 用户具有权限：{}, 访问", loginUser.getUser().getUserId(),
//                    loginUser.getUsername(), permission, loginUser.getUser().getPermissions());
//        } else {
//            log.info("用户userId={}, userName={} 权限不足以访问[{}]", loginUser.getUser().getUserId(), loginUser.getUsername(), permission);
//        }
        return false;
    }
}