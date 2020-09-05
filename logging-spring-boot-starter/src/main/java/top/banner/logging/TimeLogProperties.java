package top.banner.logging;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author XGL
 */
@ConfigurationProperties(prefix = "time.log")
public class TimeLogProperties {
    private Boolean enable;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
