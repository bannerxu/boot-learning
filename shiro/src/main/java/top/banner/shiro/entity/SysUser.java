package top.banner.shiro.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author XGL
 */
@Entity(name = "sys_user")
@Data
public class SysUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String account;

    private String password;

    @Transient
    private String token;

}
