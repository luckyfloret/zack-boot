package cn.hmg.zackblog.module.system.mapper.logger;

import cn.hmg.zackblog.framework.common.enums.GlobalSuccessCodeEnum;
import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.mybatisplus.core.mapper.BaseMapperExtend;
import cn.hmg.zackblog.framework.mybatisplus.core.query.LambdaQueryWrapperExtend;
import cn.hmg.zackblog.module.system.controller.admin.logger.vo.operatelog.OperateLogPageReqVO;
import cn.hmg.zackblog.module.system.entity.logger.LoginLog;
import cn.hmg.zackblog.module.system.entity.logger.OperateLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 操作日志 Mapper 接口
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Mapper
public interface OperateLogMapper extends BaseMapperExtend<OperateLog> {

    default PageResult<OperateLog> getPage(OperateLogPageReqVO operateLogPageReqVO) {
        LambdaQueryWrapperExtend<OperateLog> lambdaQueryWrapperExtend = new LambdaQueryWrapperExtend<>();

        lambdaQueryWrapperExtend
                .likeIfExists(OperateLog::getModule, operateLogPageReqVO.getModule())
                .likeIfExists(OperateLog::getUsername, operateLogPageReqVO.getUsername())
                .eqIfExists(OperateLog::getUserType, operateLogPageReqVO.getUserType())
                .eqIfExists(OperateLog::getType, operateLogPageReqVO.getType())
                .geIfExists(OperateLog::getOperateTime, operateLogPageReqVO.getStartTime())
                .leIfExists(OperateLog::getOperateTime, operateLogPageReqVO.getEndTime())
        ;

        if (Boolean.TRUE.equals(operateLogPageReqVO.getResult())) {
            lambdaQueryWrapperExtend.eq(OperateLog::getResultCode, GlobalSuccessCodeEnum.SUCCESS.getCode());
        } else if (Boolean.FALSE.equals(operateLogPageReqVO.getResult())) {
            lambdaQueryWrapperExtend.ne(OperateLog::getResultCode, GlobalSuccessCodeEnum.SUCCESS.getCode());
        }

        return page(operateLogPageReqVO, lambdaQueryWrapperExtend);
    }
}
