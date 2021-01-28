package top.banner.snowflake.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import top.banner.snowflake.utils.SnowflakeIdWorker;

/**
 * @author XGL
 */
@Data
@Configuration
@PropertySource("classpath:snowflake.properties")
@ConfigurationProperties(prefix = "snowflake")
public class IDGenderConfig {

    //数据中心[0,31] 配置文件中不配置就是0
    private long datacenterId;

    //机器标识[0,31] 配置文件中不配置就是0
    private long machineId;

    @Bean
    public SnowflakeIdWorker getSnowFlakeFactory() {
        return new SnowflakeIdWorker(datacenterId, machineId);
    }
}
