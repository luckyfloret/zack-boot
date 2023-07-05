package cn.hmg.zackblog.framework.core.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-05 9:53
 * @description: 默认自动填充器
 */
@Slf4j
@Component
public class DefaultMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert time and update time fill...");
        this.strictInsertFill(metaObject, "createTime", LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update time fill...");
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
    }
}
