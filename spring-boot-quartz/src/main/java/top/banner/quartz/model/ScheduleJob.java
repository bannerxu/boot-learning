package top.banner.quartz.model;


import lombok.Data;
import top.banner.quartz.enums.ScheduleStatus;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class ScheduleJob implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 9150018026309004044L;

    /**
     * 任务id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;

    /**
     * spring bean名称
     */
    @NotBlank(message = "bean名称不能为空")
    private String beanName;

    /**
     * 方法名
     */

    @NotBlank(message = "方法名称不能为空")
    private String methodName;

    /**
     * 参数
     */
    private String params;

    /**
     * cron表达式
     */
    @NotBlank(message = "cron表达式不能为空")
    private String cronExpression;

    /**
     * 任务状态  0：正常  1：暂停
     */
    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    /**
     * 备注
     */
    private String remark;

    /*
     * 任务组
     */
    private String groupName;

    /**
     * 创建时间
     */
    private Date createTime = new Date();
}