package cn.hmg.zackblog.module.system.mapper.logger;

import cn.hmg.zackblog.framework.common.enums.GlobalSuccessCodeEnum;
import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.mybatisplus.core.mapper.BaseMapperExtend;
import cn.hmg.zackblog.framework.mybatisplus.core.query.LambdaQueryWrapperExtend;
import cn.hmg.zackblog.module.system.controller.admin.logger.vo.loginlog.LoginLogPageReqVO;
import cn.hmg.zackblog.module.system.entity.logger.LoginLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 登录日志 Mapper 接口
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Mapper
public interface LoginLogMapper extends BaseMapperExtend<LoginLog> {

    default PageResult<LoginLog> getPage(LoginLogPageReqVO loginLogPageReqVO){
        LambdaQueryWrapperExtend<LoginLog> lambdaQueryWrapperExtend = new LambdaQueryWrapperExtend<LoginLog>()
                .eqIfExists(LoginLog::getUserType, loginLogPageReqVO.getUserType())
                .likeIfExists(LoginLog::getUserIp, loginLogPageReqVO.getUserIp())
                .likeIfExists(LoginLog::getUsername, loginLogPageReqVO.getUsername())
                .geIfExists(LoginLog::getCreateTime, loginLogPageReqVO.getStartTime())
                .leIfExists(LoginLog::getCreateTime, loginLogPageReqVO.getEndTime()
        );
        if (Boolean.TRUE.equals(loginLogPageReqVO.getResult())) {
            lambdaQueryWrapperExtend.eq(LoginLog::getResult, GlobalSuccessCodeEnum.SUCCESS.getCode());
        } else if (Boolean.FALSE.equals(loginLogPageReqVO.getResult())) {
            lambdaQueryWrapperExtend.ne(LoginLog::getResult, GlobalSuccessCodeEnum.SUCCESS.getCode());
        }

        return page(loginLogPageReqVO, lambdaQueryWrapperExtend);
    }
}
