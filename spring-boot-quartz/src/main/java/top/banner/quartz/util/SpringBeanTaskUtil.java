package top.banner.quartz.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;
import top.banner.quartz.model.ScheduleJob;

import java.lang.reflect.Method;


/**
 * 定时任务spring bean 执行定时任务
 *
 * @author LGH
 */
@Slf4j
public class SpringBeanTaskUtil {

    public static void invokeMethod(ScheduleJob scheduleJob) {
        Object target = SpringContextUtils.getBean(scheduleJob.getBeanName());
        try {
            if (StringUtils.isNotEmpty(scheduleJob.getParams())) {
                Method method = target.getClass().getDeclaredMethod(scheduleJob.getMethodName(), String.class);
                ReflectionUtils.makeAccessible(method);
                method.invoke(target, scheduleJob.getParams());
            } else {
                Method method = target.getClass().getDeclaredMethod(scheduleJob.getMethodName());
                ReflectionUtils.makeAccessible(method);
                method.invoke(target);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("执行定时任务失败", e);
        }
    }
}
