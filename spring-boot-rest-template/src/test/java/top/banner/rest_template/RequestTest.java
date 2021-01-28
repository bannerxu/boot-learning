package top.banner.rest_template;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author XGL
 */
@SpringBootTest
public class RequestTest {
    @Resource
    private RestTemplate restTemplate;

    public void helloTest() {
        String forObject = restTemplate.getForObject("http://localhost:8080/hello", String.class);
    }
}
