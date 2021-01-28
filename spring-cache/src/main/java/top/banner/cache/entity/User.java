package top.banner.cache.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import top.banner.cache.ext.LocalDateTimeConverter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

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

    @JsonSerialize(using = LocalDateTimeConverter.class)
    private LocalDateTime createTime = LocalDateTime.now();

    private LocalDateTime updateTime = LocalDateTime.now();

}
