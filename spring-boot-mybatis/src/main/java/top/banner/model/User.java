package top.banner.model;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author XGL
 */
@Table(name = "users")
@Data
public class User {
    @Id
    private Long id;
    private String username;
    private String password;
}
