package top.banner.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * @author XGL
 */
@Data
@Entity
public class User implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private Integer groupId;

    private Integer age;

    private Date updateTime;

    private LocalDateTime createTime = LocalDateTime.now();

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
