package cn.hmg.zackblog.module.system.mapper.logger;

import cn.hmg.zackblog.module.system.entity.logger.OperateLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 操作日志 Mapper 接口
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Mapper
public interface OperateLogMapper extends BaseMapper<OperateLog> {

}
