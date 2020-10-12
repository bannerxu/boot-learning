package top.banner.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 自动填充处理器
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {

        /*
            优化
            包含自动填充字段set方法时才执行，要不然就忽略
            不同字段，执行不同的自动填充逻辑
        */
        boolean hasSetter = metaObject.hasSetter("createTime");
        if (hasSetter) {
            System.out.println("insertFill~~");
            this.strictInsertFill(metaObject, "createTime", LocalDateTime::now, LocalDateTime.class);

        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        /*
            没有设置值的时候自动填充，如果已经设置了值，就不要自动填充
         */
        Object updateTimeValue = getFieldValByName("updateTime", metaObject);
        if (updateTimeValue == null) {
            System.out.println("updateFill~~");
            this.strictUpdateFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
        }
    }
}