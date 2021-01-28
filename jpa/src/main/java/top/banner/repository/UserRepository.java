package top.banner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import top.banner.entity.User;

import java.util.List;
import java.util.Map;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    @Query(value = "select u.username  ,id as a from user u", nativeQuery = true)
    List<Map<String, Object>> groupBy();
}