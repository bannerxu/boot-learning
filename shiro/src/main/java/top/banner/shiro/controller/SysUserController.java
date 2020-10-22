package top.banner.shiro.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.banner.shiro.dao.SysUserRepository;
import top.banner.shiro.entity.SysUser;
import top.banner.shiro.service.RedisKeyPrefix;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author XGL
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController {
    @Resource
    private SysUserRepository sysUserRepository;
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @GetMapping("/login/{username}/{password}")
    public SysUser login(@PathVariable String username, @PathVariable String password) {
        SysUser sysUser = sysUserRepository.findByAccount(username);

        if (sysUser == null) {
            throw new RuntimeException("账号不存在");
        }
        deleteRedisCache(sysUser.getId());
        String token = addRedisCache(sysUser.getId());
        sysUser.setToken(token);
        return sysUser;
    }


    private void deleteRedisCache(Integer sysUserId) {

        final String token = stringRedisTemplate.opsForValue().get(RedisKeyPrefix.buildManagerIdToToken(sysUserId));

        if (!org.springframework.util.StringUtils.isEmpty(token)) {
            stringRedisTemplate.delete(Arrays.asList(RedisKeyPrefix.buildTokenToSysUserId(token),
                    RedisKeyPrefix.buildSysUserIdToToken(sysUserId)));
        }
    }

    private String addRedisCache(Integer sysUserId) {
        String token = "sysUser_" + UUID.randomUUID().toString().replace("-", "");
        final String managerIdStr = String.valueOf(sysUserId);
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set(RedisKeyPrefix.buildTokenToSysUserId(token), managerIdStr, 7 * 24, TimeUnit.HOURS);
        operations.set(RedisKeyPrefix.buildSysUserIdToToken(sysUserId), token, 7 * 24, TimeUnit.HOURS);
        return token;
    }


    @GetMapping("/1")
    public String hello() {
        return "这个接口任何人都可以访问";
    }

    @GetMapping("/2")
//    @RequiresPermissions(logical = Logical.OR, value = {"ROOT"})
    @RequiresRoles(logical = Logical.OR, value = "groupOwner")
    public String hello2() {
        return "这个接口只有群主才能访问";
    }

    @GetMapping("/3")
    @RequiresPermissions(logical = Logical.OR, value = {"ROOT"})
//    @RequiresRoles(logical = Logical.OR,value = "groupOwner")
    public String hello3() {
        return "这个接口只有root才能访问";
    }
}
