package top.banner.security.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.banner.security.bean.Who;
import top.banner.security.model.User;

/**
 * @author XGL
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("info")
    public User info(@AuthenticationPrincipal Who who) {
        return who.getUserDetail();
    }
}
