package cn.hmg.zackblog.framework.mybatisplus.core.handler;

import cn.hmg.zackblog.framework.security.core.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-05 9:53
 * @description: 默认自动填充器
 */
@Slf4j
public class DefaultMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert time and update time fill...");
        this.strictInsertFill(metaObject, "createTime", LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
        Long loginUserId = SecurityUtils.getLoginUserId();
        if (Objects.nonNull(loginUserId)) {
            setFieldValByName("creator", loginUserId, metaObject);
            setFieldValByName("updater", loginUserId, metaObject);
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update time fill...");
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
        Long loginUserId = SecurityUtils.getLoginUserId();
        if (Objects.nonNull(loginUserId)) {
            setFieldValByName("updater", loginUserId, metaObject);
        }
    }
}
