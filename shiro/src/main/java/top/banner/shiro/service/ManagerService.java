package top.banner.shiro.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.banner.shiro.dao.ManagerRepository;
import top.banner.shiro.dao.RoleGroupRepository;
import top.banner.shiro.entity.Authorities;
import top.banner.shiro.entity.Manager;
import top.banner.shiro.entity.RoleGroup;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author XGL
 */
@Service
public class ManagerService {
    @Resource
    private RoleGroupRepository roleGroupRepository;
    @Resource
    private ManagerRepository managerRepository;

    @PostConstruct
    @Transactional
    public void initRole() {
        RoleGroup root = roleGroupRepository.findByName("root");
        if (root == null) {
            root = new RoleGroup();
            root.setName("root");
            ArrayList<String> list = new ArrayList<>();
            list.add("ROOT");
            list.add("ROOT1");
            list.add("ROOT2");
            root.setAuthority(new Authorities(list));
            roleGroupRepository.save(root);
        }

        RoleGroup admin = roleGroupRepository.findByName("管理员");
        if (admin == null) {
            admin = new RoleGroup();
            admin.setName("管理员");
            ArrayList<String> list = new ArrayList<>();
            list.add("admin1");
            list.add("admin2");
            list.add("admin3");
            admin.setAuthority(new Authorities(list));
            roleGroupRepository.save(admin);
        }

        //初始化管理员
        Manager root1 = managerRepository.findByAccount("root");
        if (root1 == null) {
            root1 = new Manager();
            root1.setAccount("root");
            root1.setPassword("123456");
            root1.setRole(root);
            managerRepository.save(root1);
        }

        Manager admin1 = managerRepository.findByAccount("admin");
        if (admin1 == null) {
            admin1 = new Manager();
            admin1.setAccount("admin");
            admin1.setPassword("123456");
            admin1.setRole(admin);
            managerRepository.save(admin1);
        }
    }
}
