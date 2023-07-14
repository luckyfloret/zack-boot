package cn.hmg.zackblog.module.system.service.logger;

import cn.hmg.zackblog.module.system.convert.logger.LoginLogConvert;
import cn.hmg.zackblog.module.system.entity.logger.LoginLog;
import cn.hmg.zackblog.module.system.mapper.logger.LoginLogMapper;
import cn.hmg.zackblog.module.system.service.logger.dto.LoginLogCreateDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    @Resource
    private LoginLogMapper loginLogMapper;

    @Override
    public void createLoginLog(LoginLogCreateDTO loginLogCreateDTO) {
        LoginLog loginLog = LoginLogConvert.INSTANCE.convert(loginLogCreateDTO);
        loginLogMapper.insert(loginLog);
    }
}
