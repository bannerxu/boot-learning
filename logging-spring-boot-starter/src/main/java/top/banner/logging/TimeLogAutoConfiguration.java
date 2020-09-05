package top.banner.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author XGL
 */
@Configuration
@Aspect
@EnableAspectJAutoProxy
@ConditionalOnProperty(prefix = "time.log", name = "enable", havingValue = "true", matchIfMissing = true)
public class TimeLogAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(TimeLogAutoConfiguration.class);


    @Around("@annotation(TimeLog)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        //public java.long.String top.banner.xxx.controller.hello()
        String name = proceedingJoinPoint.getSignature().toLongString().split(" ")[2];
        long startTime = System.currentTimeMillis();
        Object proceed = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();

        log.info("方法 {} 耗时 {} ms", name, endTime - startTime);
        return proceed;
    }
}
