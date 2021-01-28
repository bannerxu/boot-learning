package top.banner.shiro.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String account;

    private String password;

    @ManyToOne
    private RoleGroup role;

    @Transient
    private String token;

    public Manager() {
    }
}