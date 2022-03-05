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
        log.info("新增操作公共字段自动填充...");
        log.info(metaObject.toString());
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("createUser", new Long(123));
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", new Long(123));
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("更改操作公共字段自动填充...");
        log.info(metaObject.toString());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", new Long(123));
    }
}
