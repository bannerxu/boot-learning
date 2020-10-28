package top.banner.security.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author XGL
 */
@Entity
@Data
public class SysUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String authority;


    public List<String> getAuthorityString() {
        if (authority == null) {
            return new ArrayList<>();
        } else {
            return Arrays.stream(authority.split(",")).collect(Collectors.toList());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getAuthorityString()
                .stream()
                .map(it -> {
                    String x = it.toUpperCase(Locale.CHINA);
                    if (!x.startsWith("ROLE_")) {
                        x = "ROLE_" + x;
                    }
                    return new SimpleGrantedAuthority(x);
                }).collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
