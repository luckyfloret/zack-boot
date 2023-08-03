package cn.hmg.zackblog.framework.operatelog.core.service;

import cn.hmg.zackblog.framework.operatelog.core.api.OperateLogApi;
import cn.hmg.zackblog.framework.operatelog.core.pojo.OperateLog;
import lombok.RequiredArgsConstructor;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-31 16:35
 * @description:
 */
@RequiredArgsConstructor
public class OperateLogFrameworkServiceImpl implements OperateLogFrameworkService{

    private final OperateLogApi operateLogApi;
    @Override
    public void createOperateLog(OperateLog operateLog) {
        operateLogApi.createOperateLog(operateLog);
    }
}
