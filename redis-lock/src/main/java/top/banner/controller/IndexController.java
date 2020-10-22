package top.banner.controller;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.banner.annotation.RedisLock;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author XGL
 */
@RestController
public class IndexController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedissonClient redissonClient;

    @GetMapping("/deduct_stock")
    public String deductStock() {
        String lockKey = "lockKey";
//        String clientId = UUID.randomUUID().toString();
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        RLock lock = redissonClient.getLock(lockKey);

        try {
//            Boolean result = ops.setIfAbsent(lockKey, clientId, 10, TimeUnit.SECONDS);
//            if (!result) {
//                return "error";
//            }
            lock.lock(30, TimeUnit.SECONDS);

            int stock = Integer.parseInt(ops.get("stock"));

            if (stock > 0) {
                System.out.println("剩余库存：" + stock);
                stock = stock - 1;
                ops.set("stock", stock + "");
            } else {
                System.out.println("库存不足~~");
            }

        } finally {
            lock.unlock();
//            if (clientId.equals(ops.get(lockKey))) {
//                stringRedisTemplate.delete(lockKey);//释放锁
//            }
        }
        return "end";
    }


    @GetMapping("/deduct_stock_redis")
    @RedisLock(expire = 5, timeUnit = TimeUnit.SECONDS)
    public String stock() {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        int stock = Integer.parseInt(ops.get("stock"));
        if (stock > 0) {
            System.out.println("剩余库存：" + stock);
            stock = stock - 1;
            ops.set("stock", stock + "");
        } else {
            System.out.println("库存不足~~");
        }
        return "end";
    }
}
