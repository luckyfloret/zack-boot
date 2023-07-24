package cn.hmg.zackblog.module.system.convert.logger;

import cn.hmg.zackblog.module.system.entity.logger.LoginLog;
import cn.hmg.zackblog.module.system.service.logger.dto.LoginLogCreateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-14 13:47
 * @description: 登录日志convert接口
 */
@Mapper
public interface LoginLogConvert {
    LoginLogConvert INSTANCE = Mappers.getMapper(LoginLogConvert.class);

    /**
     * LoginLogCreateDTO转换为LoginLog Entity
     * @param loginLogCreateDTO 创建登录日志dto
     * @return LoginLog
     */
    LoginLog convert(LoginLogCreateDTO loginLogCreateDTO);
}
