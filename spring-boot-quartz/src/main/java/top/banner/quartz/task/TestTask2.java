package top.banner.quartz.task;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xgl
 */
@Component("testTask2")
public class TestTask2 {
    public void executeInternal() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("TestQuartz02----" + sdf.format(new Date()));
    }
}

