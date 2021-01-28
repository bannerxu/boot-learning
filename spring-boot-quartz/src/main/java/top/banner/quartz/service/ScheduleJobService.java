package top.banner.quartz.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import top.banner.quartz.model.ScheduleJob;

import java.util.List;

/**
 * @author XGL
 */
public interface ScheduleJobService {
    /**
     * @param pageable 分页参数
     * @return 分页列表
     */
    Page<ScheduleJob> page(Pageable pageable);

    /**
     * @param jobId 任务id
     * @return 任务详情
     */
    ScheduleJob info(Long jobId);

    /**
     * 保存并启动
     *
     * @param scheduleJob 任务
     */
    void saveAndStart(ScheduleJob scheduleJob);

    /**
     * 修改定时任务
     *
     * @param scheduleJob 任务
     */
    void updateScheduleJob(ScheduleJob scheduleJob);

    /**
     * 删除定时任务
     *
     * @param jobIds 要删除的定时任务id
     */
    void deleteBatch(List<Long> jobIds);

    /**
     * 启动定时任务
     *
     * @param jobIds 要启动的定时任务id
     */
    void run(List<Long> jobIds);

    /**
     * 暂停定时任务
     *
     * @param jobIds 要暂停的定时任务id
     */
    void pause(List<Long> jobIds);

    /**
     * 恢复定时任务
     *
     * @param jobIds 要恢复的定时任务id
     */
    void resume(List<Long> jobIds);
}
