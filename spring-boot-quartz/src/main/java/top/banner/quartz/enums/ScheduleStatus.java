package top.banner.quartz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ScheduleStatus {

    /**
     * 正常
     */
    NORMAL,
    /**
     * 暂停
     */
    PAUSE
}
