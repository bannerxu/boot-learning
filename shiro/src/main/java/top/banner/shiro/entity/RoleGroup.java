package top.banner.shiro.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author XGL
 */
@Entity
@Data
public class RoleGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private Authorities authority;


}
