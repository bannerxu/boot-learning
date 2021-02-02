package top.banner.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

@Aspect
@Component
public class CurlAspect {
    private static final Logger log = LoggerFactory.getLogger(CurlAspect.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Before("execution(public * top.banner.controller.*A.*(..))")
    public void before(JoinPoint point) {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        log.info("【请求 URL】：{}", request.getRequestURL());
        log.info("【请求 IP】：{}", request.getRemoteAddr());
        log.info("【请求类名】：{}，【请求方法名】：{}", point.getSignature().getDeclaringTypeName(), point.getSignature().getName());
        Object[] args = point.getArgs();

        log.info("【body】：{}，", objectMapper.writeValueAsString(args));
        Map<String, String[]> parameterMap = request.getParameterMap();
        log.info("【请求参数】：{}，", objectMapper.writeValueAsString(parameterMap));

    }

}