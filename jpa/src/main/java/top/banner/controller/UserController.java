package top.banner.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.banner.entity.User;
import top.banner.service.UserService;

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

    @GetMapping
    public List<User> all() {
        return userService.all();
    }
}
