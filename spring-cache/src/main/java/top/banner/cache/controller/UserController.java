package top.banner.cache.controller;

import org.springframework.web.bind.annotation.*;
import top.banner.cache.service.UserService;
import top.banner.entity.User;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author XGL
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Resource
    private UserService userService;


    /**
     * 查找所有
     */
    @GetMapping
    public List<User> getAll() {
        return userService.all();
    }

    /**
     * 根据id获取用户
     */
    @GetMapping("/{id}")
    public User info(@PathVariable Long id) {
        return userService.info(id);
    }

    /**
     * 新增用户
     */
    @PostMapping
    public void add(@RequestBody User user) {
        userService.add(user);
    }

    /**
     * 修改用户
     */
    @PutMapping
    public void update(@RequestBody User user) {
        userService.update(user);
    }


    @GetMapping("/deleteAllCache")
    public void deleteAllCache() {
        userService.deleteAll1();
    }
}
