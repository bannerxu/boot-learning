package top.banner.quartz.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.banner.quartz.config.ScheduleManager;
import top.banner.quartz.enums.ScheduleStatus;
import top.banner.quartz.model.ScheduleJob;
import top.banner.quartz.model.ScheduleJobRepository;
import top.banner.quartz.service.ScheduleJobService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author XGL
 */
@Service
public class ScheduleJobServiceImpl implements ScheduleJobService {
    @Resource
    private ScheduleJobRepository scheduleJobRepository;
    @Resource
    private ScheduleManager scheduleManager;

    @Override
    public Page<ScheduleJob> page(Pageable pageable) {
        return scheduleJobRepository.findAll(pageable);
    }

    @Override
    public ScheduleJob info(Long jobId) {
        return scheduleJobRepository.findById(jobId).orElse(null);
    }

    @Override
    @Transactional
    public void saveAndStart(ScheduleJob scheduleJob) {
        boolean exist = scheduleJobRepository.existsByBeanNameAndMethodName(scheduleJob.getBeanName(), scheduleJob.getMethodName());
        if (exist) {
            return;
        }
        scheduleJobRepository.save(scheduleJob);
        scheduleManager.createScheduleJob(scheduleJob);
    }

    @Override
    @Transactional
    public void updateScheduleJob(ScheduleJob scheduleJob) {
        if (scheduleJobRepository.existsByBeanNameAndMethodNameAndJobId(scheduleJob.getBeanName(), scheduleJob.getMethodName(), scheduleJob.getJobId())) {
            throw new RuntimeException("定时任务已存在");
        }
        scheduleJobRepository.save(scheduleJob);
        scheduleManager.updateScheduleJob(scheduleJob);
    }

    @Override
    @Transactional
    public void deleteBatch(List<Long> jobIds) {
        List<ScheduleJob> all = scheduleJobRepository.findAllById(jobIds);
        all.forEach(it -> scheduleManager.deleteScheduleJob(it));
        scheduleJobRepository.deleteAll(all);
    }

    @Override
    public void run(List<Long> jobIds) {
        scheduleJobRepository.findAllById(jobIds).forEach(it -> scheduleManager.run(it));
    }

    @Override
    @Transactional
    public void pause(List<Long> jobIds) {
        scheduleJobRepository.findAllById(jobIds).forEach(it -> {
            scheduleManager.pauseJob(it);
            it.setStatus(ScheduleStatus.PAUSE);
        });
    }

    @Override
    @Transactional
    public void resume(List<Long> jobIds) {
        scheduleJobRepository.findAllById(jobIds).forEach(it -> {
            scheduleManager.resumeJob(it);
            it.setStatus(ScheduleStatus.PAUSE);
        });
    }

}
