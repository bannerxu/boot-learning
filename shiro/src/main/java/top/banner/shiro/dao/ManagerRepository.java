
package top.banner.shiro.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import top.banner.shiro.entity.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Integer>, JpaSpecificationExecutor<Manager> {
    Manager findByAccount(String account);
}
