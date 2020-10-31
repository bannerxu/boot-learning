package top.banner.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import top.banner.config.SpringConfig;

/**
 * @author XGL
 */
@SpringBootApplication
@Import(SpringConfig.class)
public class CacheApplication {
    public static void main(String[] args) {
        SpringApplication.run(CacheApplication.class, args);
    }
}
