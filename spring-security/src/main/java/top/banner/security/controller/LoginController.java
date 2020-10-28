package top.banner.security.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.banner.security.bean.LoginVO;
import top.banner.security.model.User;
import top.banner.security.service.UserService;

import javax.annotation.Resource;

/**
 * @author XGL
 */
@RequestMapping("/login")
@RestController
public class LoginController {
    @Resource
    private UserService userService;


    @GetMapping
    public LoginVO login(@RequestParam String username,
                         @RequestParam String password) {

        User user = userService.login(username, password);

        LoginVO loginVO = new LoginVO();
        BeanUtils.copyProperties(user, loginVO);
        loginVO.setToken(userService.readToken(user));
        return loginVO;
    }


}
