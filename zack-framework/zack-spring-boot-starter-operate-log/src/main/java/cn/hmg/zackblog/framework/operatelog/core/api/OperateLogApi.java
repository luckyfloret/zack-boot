package cn.hmg.zackblog.framework.operatelog.core.api;

import cn.hmg.zackblog.framework.operatelog.core.pojo.OperateLog;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-31 16:37
 * @description: 日志api，提供给system模块实现
 */
public interface OperateLogApi {
    void createOperateLog(OperateLog operateLog);
}
