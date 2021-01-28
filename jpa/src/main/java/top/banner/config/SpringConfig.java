package top.banner.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author XGL
 */
@Configuration
@EnableJpaRepositories(basePackages = "top.banner")
@ComponentScan(basePackages = "top.banner")
public class SpringConfig {

}
