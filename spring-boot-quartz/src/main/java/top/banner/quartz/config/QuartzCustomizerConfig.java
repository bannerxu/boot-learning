package top.banner.quartz.config;

import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * 添加Scheduler配置
 * Scheduler(任务调度器)
 */
@Configuration
public class QuartzCustomizerConfig implements SchedulerFactoryBeanCustomizer {
    @Override
    public void customize(SchedulerFactoryBean schedulerFactoryBean) {
        schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(true);
    }
}