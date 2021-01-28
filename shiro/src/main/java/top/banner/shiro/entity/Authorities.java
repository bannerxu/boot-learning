package top.banner.shiro.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author XGL
 */
@Embeddable
@Data
@NoArgsConstructor
public class Authorities implements Serializable {
    @Column(length = 1024)
    private String authority;

    public Authorities(List<String> list) {
        this.authority = listToString(list);
    }

    private static String listToString(List<String> list) {
        if (list == null) {
            return null;
        } else {
            return String.join(",", list);
        }
    }

    public List<String> asList() {
        String[] split = authority.split(",");
        return Arrays.stream(split).distinct().collect(Collectors.toList());
    }
}
