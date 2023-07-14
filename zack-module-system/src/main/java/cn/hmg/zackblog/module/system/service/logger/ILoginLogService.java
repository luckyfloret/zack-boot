package cn.hmg.zackblog.module.system.service.logger;

import cn.hmg.zackblog.module.system.entity.logger.LoginLog;
import cn.hmg.zackblog.module.system.service.logger.dto.LoginLogCreateDTO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 登录日志 服务类
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
public interface ILoginLogService extends IService<LoginLog> {

    /**
     * 创建登录日志
     * @param loginLogCreateDTO 创建登录日志dto
     */
    void createLoginLog(LoginLogCreateDTO loginLogCreateDTO);
}
