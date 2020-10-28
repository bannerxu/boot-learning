package top.banner.security.bean;

import lombok.Data;

/**
 * @author XGL
 */
@Data
public class LoginVO {
    private Long id;

    private String username;

    private String password;

    private String token;

}
