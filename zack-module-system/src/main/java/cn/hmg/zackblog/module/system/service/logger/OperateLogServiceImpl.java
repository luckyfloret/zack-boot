package cn.hmg.zackblog.module.system.service.logger;

import cn.hmg.zackblog.module.system.entity.logger.OperateLog;
import cn.hmg.zackblog.module.system.mapper.logger.OperateLogMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志 服务实现类
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Service
public class OperateLogServiceImpl extends ServiceImpl<OperateLogMapper, OperateLog> implements IOperateLogService {

}
