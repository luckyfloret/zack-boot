package cn.hmg.zackblog.module.system.convert.logger;

import cn.hmg.zackblog.module.system.entity.logger.OperateLog;
import cn.hmg.zackblog.module.system.service.logger.dto.OperateLogCreateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-31 16:25
 * @description: 操作日志转换
 */
@Mapper
public interface OperateLogConvert {
    OperateLogConvert INSTANCE = Mappers.getMapper(OperateLogConvert.class);

    OperateLog convert(OperateLogCreateDTO operateLogCreateDTO);
}
