package top.banner.security.service.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserVO implements Serializable {
    private static final long serialVersionUID = 1L;


    private Long id;

    private String username;

    private String password;

    private Boolean locked;

    private Boolean enabled;
}
