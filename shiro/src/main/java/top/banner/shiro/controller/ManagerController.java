package top.banner.shiro.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import top.banner.shiro.dao.ManagerRepository;
import top.banner.shiro.entity.Manager;
import top.banner.shiro.service.RedisKeyPrefix;
import top.banner.shiro.shiro.ManagerHelper;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author XGL
 */
@RestController
public class ManagerController {
    @Resource
    private ManagerRepository managerRepository;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/login/{username}/{password}")
    public Manager login(@PathVariable String username, @PathVariable String password) {
        Manager manager = managerRepository.findByAccount(username);

        deleteRedisCache(manager.getId());
        String token = addRedisCache(manager.getId());
        manager.setToken(token);
        return manager;
    }

    @GetMapping("/manage/userInfo")
    public Map<String, Object> userInfo() {
        Integer managerId = ManagerHelper.getManagerId();
        Manager manager = managerRepository.findById(managerId).orElse(null);
        assert manager != null;
        List<String> authority = manager.getRole().getAuthority().asList();
        Map<String, Object> result = new HashMap<>();
        result.put("authority", authority);
        return result;
    }

    @GetMapping("/manage/1")
    public String hello() {
        return "这个接口任何人都可以访问";
    }

    @GetMapping("/manage/2")
    @RequiresPermissions(logical = Logical.OR, value = {"ROOT"})
    public String hello2() {
        return "这个接口只有root才可以访问";
    }

    @GetMapping("/manage/3")
    @RequiresPermissions(logical = Logical.OR, value = {"admin1"})
    public String hello3() {
        return "这个接口管理员可以访问";
    }

    @GetMapping("/manage/4")
    @RequiresPermissions(logical = Logical.OR, value = {"admin6"})
    public String hello4() {
        return "谁都不可以访问";
    }


    private void deleteRedisCache(Integer userId) {

        final String token = stringRedisTemplate.opsForValue().get(RedisKeyPrefix.buildManagerIdToToken(userId));

        if (!org.springframework.util.StringUtils.isEmpty(token)) {
            stringRedisTemplate.delete(Arrays.asList(RedisKeyPrefix.buildTokenToManagerId(token), RedisKeyPrefix.buildManagerIdToToken(userId)));
        }
    }

    private String addRedisCache(Integer managerId) {
        String token = UUID.randomUUID().toString().replace("-", "");
        final String managerIdStr = String.valueOf(managerId);
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set(RedisKeyPrefix.buildTokenToManagerId(token), managerIdStr, 7 * 24, TimeUnit.HOURS);
        operations.set(RedisKeyPrefix.buildManagerIdToToken(managerId), token, 7 * 24, TimeUnit.HOURS);
        return token;
    }
}
