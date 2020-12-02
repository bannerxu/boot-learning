package top.banner.security.handler;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登陆失败处理
 */
@Component
@Slf4j
public class LoginAuthFailedHandler implements AuthenticationFailureHandler {

    /**
     * {@inheritDoc}
     */
    @Override
    @SneakyThrows
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response, AuthenticationException exception) {
// TODO: 2020/11/25

//        if (!(exception instanceof BaseYamiAuth2Exception)) {
//            return;
//        }
//
//        BaseYamiAuth2Exception auth2Exception = (BaseYamiAuth2Exception) exception;
//
//        response.setCharacterEncoding(CharsetUtil.UTF_8);
//        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
//        response.setStatus(auth2Exception.getHttpErrorCode());
//        PrintWriter printWriter = response.getWriter();
//        printWriter.append(auth2Exception.getMessage());
    }

}