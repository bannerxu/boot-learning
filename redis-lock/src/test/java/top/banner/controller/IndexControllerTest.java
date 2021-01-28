package top.banner.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.banner.service.LockService;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class IndexControllerTest {
    @Resource
    private LockService lockService;

    @Test
    public void test() {
        lockService.onlyOne("s");
        System.out.println("=============");
        lockService.s("xx");
        System.out.println("=============");
        lockService.b();
    }




}