package top.banner.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;
import top.banner.security.Result;
import top.banner.security.ServletUtils;

import javax.servlet.ServletException;
import java.io.IOException;

@Slf4j
@Component
public class SessionInformationExpiredHandler implements SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent sessionInformationExpiredEvent) throws IOException, ServletException {
        ServletUtils.render(sessionInformationExpiredEvent.getRequest(),
                sessionInformationExpiredEvent.getResponse(), Result.build("USER_MAX_LOGIN"));
    }
}