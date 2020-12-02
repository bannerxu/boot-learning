package top.banner.service;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import top.banner.annotation.RedisLock;

import javax.annotation.Resource;

/**
 * @author XGL
 */
@Service
public class LockService {

    @Resource
    private ApplicationContext applicationContext;

    public String onlyOne(String s) {
        return s("ss");
    }

    public String b() {
        return applicationContext.getBean(LockService.class).s("sfs");

    }

    @RedisLock(key = "#p0")
    public String s(String s) {
        return System.currentTimeMillis() + "";
    }

}
