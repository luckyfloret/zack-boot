package cn.hmg.zackblog.module.system.service.logger;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.system.controller.admin.logger.vo.operatelog.OperateLogPageReqVO;
import cn.hmg.zackblog.module.system.entity.logger.OperateLog;
import cn.hmg.zackblog.module.system.service.logger.dto.OperateLogCreateDTO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 操作日志 服务类
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
public interface IOperateLogService extends IService<OperateLog> {

    void createOperateLog(OperateLogCreateDTO operateLogCreateDTO);

    PageResult<OperateLog> getPage(OperateLogPageReqVO operateLogPageReqVO);

    OperateLog getOperateLogById(Long id);
}
