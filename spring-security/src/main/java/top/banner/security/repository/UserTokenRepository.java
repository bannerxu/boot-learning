package top.banner.security.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import top.banner.security.model.UserToken;

public interface UserTokenRepository extends JpaRepository<UserToken, String> {
    UserToken findTop1ByUserIdAndExpiredFalseOrderByCreateTimeDesc(Long userId);
}