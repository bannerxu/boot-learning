package top.banner.quartz.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import top.banner.quartz.model.ScheduleJob;

/**
 * 定时任务事件
 */
@Getter
@AllArgsConstructor
public class ScheduleJobEvent {

    private final ScheduleJob scheduleJob;
}
