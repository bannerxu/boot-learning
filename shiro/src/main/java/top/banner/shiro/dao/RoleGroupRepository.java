
package top.banner.shiro.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import top.banner.shiro.entity.RoleGroup;

public interface RoleGroupRepository extends JpaRepository<RoleGroup, Integer>, JpaSpecificationExecutor<RoleGroup> {
    RoleGroup findByName(String name);
}
