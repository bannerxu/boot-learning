package top.banner.quartz.model;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author XGL
 */
public interface ScheduleJobRepository extends JpaRepository<ScheduleJob, Long> {
    boolean existsByBeanNameAndMethodName(String beanName, String methodName);

    boolean existsByBeanNameAndMethodNameAndJobId(String beanName, String methodName, Long jobId);
}
