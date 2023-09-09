package cn.hmg.zackblog.framework.operatelog.core.service;

import cn.hmg.zackblog.framework.operatelog.core.pojo.OperateLog;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-31 16:12
 * @description: 日志接口
 */
public interface OperateLogFrameworkService {
    /**
     * 新增操作日志
     * @param operateLog 操作日志
     */
    void createOperateLog(OperateLog operateLog);
}
