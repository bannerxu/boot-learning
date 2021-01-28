package top.banner.quartz.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class ScheduleJobLog {
    /**
     * 任务日志id
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    /**
     * 任务id
     */
    private Long jobId;

    /**
     * spring bean名称
     */
    private String beanName;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 参数
     */
    private String params;

    /**
     * 任务状态    1：成功    0：失败
     */
    private Integer status;

    /**
     * 失败信息
     */
    @Column(length = 2048)
    private String error;

    /**
     * 耗时(单位：毫秒)
     */
    private Integer times;

    /**
     * 创建时间
     */
    private Date createTime;
}