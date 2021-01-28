package top.banner.quartz.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import top.banner.quartz.model.ScheduleJob;
import top.banner.quartz.service.ScheduleJobService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/sys/schedule")
public class ScheduleJobController {
    @Resource
    private ScheduleJobService scheduleJobService;

    /**
     * 定时任务列表
     */
    @GetMapping("/page")
    public Page<ScheduleJob> page(@PageableDefault Pageable pageable) {
        return scheduleJobService.page(pageable);
    }

    /**
     * 定时任务信息
     */
    @GetMapping("/info/{jobId}")
    public ScheduleJob info(@PathVariable("jobId") Long jobId) {
        return scheduleJobService.info(jobId);
    }

    /**
     * 保存定时任务
     */
    @PostMapping
    public void save(@RequestBody @Valid ScheduleJob scheduleJob) {
        scheduleJobService.saveAndStart(scheduleJob);
    }

    /**
     * 修改定时任务
     */
    @PutMapping
    public void update(@RequestBody @Valid ScheduleJob scheduleJob) {
        scheduleJobService.updateScheduleJob(scheduleJob);
    }

    /**
     * 删除定时任务
     */
    @DeleteMapping
    public void delete(@RequestBody List<Long> jobIds) {
        scheduleJobService.deleteBatch(jobIds);
    }

    /**
     * 立即执行任务
     */
    @PostMapping("/run")
    public void run(@RequestBody List<Long> jobIds) {
        scheduleJobService.run(jobIds);
    }

    /**
     * 暂停定时任务
     */
    @PostMapping("/pause")
    public void pause(@RequestBody List<Long> jobIds) {
        scheduleJobService.pause(jobIds);
    }

    /**
     * 恢复定时任务
     */
    @PostMapping("/resume")
    public void resume(@RequestBody List<Long> jobIds) {
        scheduleJobService.resume(jobIds);
    }

}