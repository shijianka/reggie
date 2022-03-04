package cn.shijianka.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
    log.info("start insert fill insert...");
    log.info(metaObject.toString());
    metaObject.setValue("creatTime", LocalDateTime.now());
    metaObject.setValue("creatUser",new Long(1));
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start insert fill update...");
        log.info(metaObject.toString());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser",new Long(1));
    }
}
