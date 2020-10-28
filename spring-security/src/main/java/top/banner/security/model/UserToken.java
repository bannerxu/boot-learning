package top.banner.security.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author XGL
 */
@Entity
@Data
public class UserToken {
    @Id
    private String id;

    private Long userId;

    private LocalDateTime createTime = LocalDateTime.now();

    private Boolean expired = false;

    public UserToken() {
    }

    public UserToken(String id, Long userId) {
        this.id = id;
        this.userId = userId;
    }
}
