package top.banner.security.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.banner.security.entity.User;
import top.banner.security.service.UserService;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    protected UserService userService;


    @GetMapping("/user/page")
    @ApiOperation(value = "分页查询用户")
    @PreAuthorize("@ps.permission('system:user:list')")
    public List<User> list() {
        return userService.list();
    }

    @GetMapping("/user/info")
    @ApiOperation("获取当前用户信息")
    public User info() {
        return null;
    }
}