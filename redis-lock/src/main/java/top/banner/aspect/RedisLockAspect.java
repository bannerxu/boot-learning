package top.banner.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import top.banner.annotation.RedisLock;
import top.banner.utils.SpELUtil;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author lgh
 */
@Aspect
@Component
public class RedisLockAspect {

    private static final String REDISSON_LOCK_PREFIX = "lock:";
    @Resource
    private RedissonClient redissonClient;

    @Around("@annotation(redisLock)")
    public Object around(ProceedingJoinPoint joinPoint, RedisLock redisLock) throws Throwable {
        String spEL = redisLock.key();
        String lockName = redisLock.lockName();
        String redisKey = getRedisKey(joinPoint, lockName, spEL);
        System.out.println("redisKey：" + redisKey);
        RLock rLock = redissonClient.getLock(redisKey);

        rLock.lock(redisLock.expire(), redisLock.timeUnit());

        Object result;
        try {

            TimeUnit.SECONDS.sleep(5);
            //执行方法
            result = joinPoint.proceed();

        } finally {
            rLock.unlock();
        }
        return result;
    }

    /**
     * 将SpEL表达式转换为字符串
     *
     * @param joinPoint 切点
     * @return redisKey
     */
    private String getRedisKey(ProceedingJoinPoint joinPoint, String lockName, String spEL) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        Object target = joinPoint.getTarget();
        Object[] arguments = joinPoint.getArgs();
        return REDISSON_LOCK_PREFIX + lockName + ":" + SpELUtil.parse(target, spEL, targetMethod, arguments) + ":" + uuid();
    }

    private String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
