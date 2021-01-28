package top.banner.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.banner.model.User;
import top.banner.service.UserService;

import javax.annotation.Resource;

/**
 * @author XGL
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/{id}")
    public User getOne(@PathVariable Long id) {
        return userService.findOne(id);
    }


    @GetMapping("/page")
    public PageInfo<User> findPage() {
        //2. Lambda
        return PageHelper.startPage(1, 10)
                .doSelectPageInfo(() -> userService.findAll());
    }


}
