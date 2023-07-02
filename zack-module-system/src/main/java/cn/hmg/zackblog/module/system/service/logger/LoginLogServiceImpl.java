package cn.hmg.zackblog.module.system.service.logger;

import cn.hmg.zackblog.module.system.entity.logger.LoginLog;
import cn.hmg.zackblog.module.system.mapper.logger.LoginLogMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 登录日志 服务实现类
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {

}
