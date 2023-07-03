package cn.hmg.zackblog.module.system.mapper.logger;

import cn.hmg.zackblog.module.system.entity.logger.LoginLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 登录日志 Mapper 接口
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Mapper
public interface LoginLogMapper extends BaseMapper<LoginLog> {

}
