package top.banner.cache.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.banner.entity.User;
import top.banner.repository.UserRepository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author XGL
 */
@Service
@CacheConfig(cacheNames = {"users"})
public class UserService {
    @Resource
    private UserRepository userRepository;

    @Cacheable(key = "'user.all'") // 标志读取缓存操作，如果缓存不存在，则调用目标方法，并将结果放入缓存
    public List<User> all() {
        return userRepository.findAll();
    }

    @Cacheable(key = "#id")//如果缓存存在，直接读取缓存值；如果缓存不存在，则调用目标方法，并将结果放入缓存
    public User info(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @CachePut(key = "#user.id")//写入缓存，key为user.id,一般该注解标注在新增方法上
    @Transactional
    public User add(User user) {
        return userRepository.save(user);
    }

    @CacheEvict(key = "#user.id")//根据key清除缓存，一般该注解标注在修改和删除方法上
    public void update(User user) {
        userRepository.save(user);
    }

    @CacheEvict(allEntries = true)//方法调用后清空所有缓存
    public void deleteAll1() {
    }

    @CacheEvict(beforeInvocation = true)//方法调用前清空所有缓存
    public void deleteAll2() {
    }


}
