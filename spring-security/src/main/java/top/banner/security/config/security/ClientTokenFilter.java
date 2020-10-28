package top.banner.security.config.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * @author XGL
 */
public class ClientTokenFilter extends AbstractPreAuthenticatedProcessingFilter {
    @Override
    protected String getPreAuthenticatedPrincipal(HttpServletRequest request) {
        String token = request.getHeader("X-Token");

        if (StringUtils.isBlank(token)) {
            return null;
        }
        String id = token.split("\\+")[0];

        return "User#" + id;
    }

    @Override
    protected String getPreAuthenticatedCredentials(HttpServletRequest request) {
        return request.getHeader("X-Token");
    }

    @Override
    protected boolean principalChanged(HttpServletRequest request, Authentication currentAuthentication) {
        String s = getPreAuthenticatedPrincipal(request);
        if (s == null) {
            return false;
        }
        return super.principalChanged(request, currentAuthentication);
    }
}
