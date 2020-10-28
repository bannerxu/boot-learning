package top.banner.security.bean;

import org.springframework.security.core.userdetails.UserDetails;
import top.banner.security.model.User;

/**
 * @author XGL
 */
public interface Who extends UserDetails {

    Long getUserId();

    User getUserDetail();
}
