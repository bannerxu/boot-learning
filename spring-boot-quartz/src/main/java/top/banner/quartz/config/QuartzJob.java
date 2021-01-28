package top.banner.quartz.config;


import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.context.ApplicationEventPublisher;
import top.banner.quartz.event.ScheduleJobEvent;
import top.banner.quartz.model.ScheduleJob;

import javax.annotation.Resource;

/**
 * 该类将会被org.springframework.scheduling.quartz.SpringBeanJobFactory 实例化
 * 并使@Resource 生效
 */
@Slf4j
@DisallowConcurrentExecution
public class QuartzJob implements Job {

    /**
     * 任务调度参数key
     */
    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";
    @Resource
    private ApplicationEventPublisher publisher;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {

        ScheduleJob sysJob = (ScheduleJob) jobExecutionContext.getMergedJobDataMap().get(JOB_PARAM_KEY);
        publisher.publishEvent(new ScheduleJobEvent(sysJob));
    }

}
