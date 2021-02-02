package top.banner.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.banner.bean.User;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping
@Api("hello")
public class HelloController {
    private static final Logger log = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/hello")
    public String hello(HttpServletRequest request) {
        return "hello";
    }

    @GetMapping("/hello1/{id}")
    public String hello1(HttpServletRequest request, @PathVariable String id) {
        return "hello";
    }

    @GetMapping("/hello2/{id}")
    public String hello2(HttpServletRequest request, @PathVariable Long id, String key, Boolean isOK) {
        return id + key + isOK;

    }

    @PostMapping("/hello3")
    public User hello3(HttpServletRequest request, @RequestBody User user) {
        return user;
    }

    @PostMapping("/hello4")
    public String hello4(HttpServletRequest request, MultipartFile file) {
        return file.getOriginalFilename();
    }


    @PutMapping("/hello5")
    public User hello5(HttpServletRequest request, User user) {
        return user;
    }

    @DeleteMapping("/hello6")
    public User hello6(HttpServletRequest request, @RequestBody User user) {
        return user;
    }


}