package top.banner.snowflake.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.banner.snowflake.utils.SnowflakeIdWorker;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author XGL
 */
@RestController
@RequestMapping("/snowflake")
public class SnowFlakeController {

    @Resource
    private SnowflakeIdWorker snowflakeIdWorker;

    /**
     * 雪花算法测试
     */
    @GetMapping("/getId")
    public String genSnowFlake() {
        return snowflakeIdWorker.nextId();
    }

    /**
     * 批量生成id
     * 开始时间2020-05-27T17:01:34.176
     * 结束时间2020-05-27T17:01:34.463
     * 200000
     * 结论：300ms生成 20w个无重复的id
     */
    @GetMapping("/batchCreateID")
    public Set<String> batchCreateID() {
        int count = 200000;

        System.out.println("开始生成id......");
        ExecutorService executor = Executors.newCachedThreadPool();

        List<Integer> countList = new ArrayList<>();
        //测试生成20w个id
        for (int i = 0; i < count; i++) {
            countList.add(i);
        }

        //使用set测试是否有重复，结果没有任何重复
        Set<String> list = Collections.synchronizedSet(new HashSet<>());
        System.out.println("开始时间" + LocalDateTime.now());
        countList.parallelStream().forEach((i) -> {
            Future<String> futureTask = executor.submit(() -> snowflakeIdWorker.nextId());
            String id = null;
            try {
                id = futureTask.get();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            list.add(id);
        });
        System.out.println("结束时间" + LocalDateTime.now());
        System.out.println(list.size());
        return list;
    }
}