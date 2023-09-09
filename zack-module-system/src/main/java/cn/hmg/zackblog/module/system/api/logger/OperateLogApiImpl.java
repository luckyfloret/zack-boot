package cn.hmg.zackblog.module.system.api.logger;

import cn.hmg.zackblog.framework.operatelog.core.api.OperateLogApi;
import cn.hmg.zackblog.framework.operatelog.core.pojo.OperateLog;
import cn.hmg.zackblog.module.system.service.logger.IOperateLogService;
import cn.hmg.zackblog.module.system.service.logger.dto.OperateLogCreateDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-31 16:15
 * @description: OperateLogService实现
 */
@Service
public class OperateLogApiImpl implements OperateLogApi {

    @Resource
    private IOperateLogService operateLogService;

    @Override
    public void createOperateLog(OperateLog operateLog) {
        OperateLogCreateDTO operateLogCreateDTO = new OperateLogCreateDTO();
        BeanUtils.copyProperties(operateLog, operateLogCreateDTO);
        operateLogService.createOperateLog(operateLogCreateDTO);
    }
}
