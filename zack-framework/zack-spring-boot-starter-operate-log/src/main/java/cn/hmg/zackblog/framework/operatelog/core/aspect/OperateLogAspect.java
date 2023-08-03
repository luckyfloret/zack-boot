package cn.hmg.zackblog.framework.operatelog.core.aspect;

import cn.hmg.zackblog.framework.common.exception.BusinessException;
import cn.hmg.zackblog.framework.common.exception.enums.GlobalErrorCode;
import cn.hmg.zackblog.framework.common.pojo.CommonResult;
import cn.hmg.zackblog.framework.common.utils.json.JsonUtils;
import cn.hmg.zackblog.framework.operatelog.core.annotation.OperateLog;
import cn.hmg.zackblog.framework.operatelog.core.service.OperateLogFrameworkService;
import cn.hmg.zackblog.framework.security.core.pojo.LoginUser;
import cn.hmg.zackblog.framework.security.core.utils.SecurityUtils;
import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static cn.hmg.zackblog.framework.common.enums.GlobalSuccessCodeEnum.*;
import static cn.hmg.zackblog.framework.common.utils.servlet.ServletUtils.*;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-29 23:31
 * @description: 日志切面
 */
@Slf4j
@Aspect
public class OperateLogAspect {
    @Resource
    private OperateLogFrameworkService operateLogFrameworkService;

    @Around("@annotation(operateLog)")
    public Object around(ProceedingJoinPoint joinPoint, OperateLog operateLog) {
        LocalDateTime operateTime = LocalDateTime.now();
        try {
            //如果日志记录没开启，就不需要记录，直接执行方法
            if (!operateLog.enable()) {
                return joinPoint.proceed();
            }

            //执行方法
            Object executeResult = joinPoint.proceed();

            //记录操作日志
            recordLog(operateLog, joinPoint, executeResult, operateTime, null);

            return executeResult;
        } catch (Throwable e) {
            recordLog(operateLog, joinPoint, null, operateTime, e);
            throw new RuntimeException(e);
        }
    }

    private void recordLog(OperateLog operateLog, ProceedingJoinPoint joinPoint, Object result, LocalDateTime operateTime, Throwable exception) {
        LocalDateTime endTime = LocalDateTime.now();
        long duration = Duration.between(operateTime, endTime).toMillis();

        cn.hmg.zackblog.framework.operatelog.core.pojo.OperateLog operateLogEntity = new cn.hmg.zackblog.framework.operatelog.core.pojo.OperateLog();
        operateLogEntity.setOperateTime(operateTime);
        operateLogEntity.setDuration(Long.valueOf(duration).intValue());


        //设置结果信息
        setResultInfo(operateLogEntity, result, exception, operateLog);

        //获取OperateLog注解上的信息
        setOperateLogAnnotationInfo(operateLogEntity, joinPoint, operateLog);

        //设置方法信息
        setMethodInfo(joinPoint, operateLogEntity, operateLog);

        //获取用户信息
        setUserInfo(operateLogEntity);

        //获取请求信息
        setRequestInfo(operateLogEntity);

        log.info("operateLogEntity => {}", operateLogEntity);
        //入db
        operateLogFrameworkService.createOperateLog(operateLogEntity);
    }

    private void setRequestInfo(cn.hmg.zackblog.framework.operatelog.core.pojo.OperateLog operateLogEntity) {
        HttpServletRequest request = getRequest();
        operateLogEntity.setUserIp(getClientIp());
        operateLogEntity.setUserAgent(getUserAgent());
        operateLogEntity.setRequestUrl(request.getRequestURI());
        operateLogEntity.setRequestMethod(request.getMethod());
    }

    private void setUserInfo(cn.hmg.zackblog.framework.operatelog.core.pojo.OperateLog operateLogEntity) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        assert loginUser != null;
        operateLogEntity.setUserId(loginUser.getUserId());
        operateLogEntity.setUsername(loginUser.getUsername());
        operateLogEntity.setUserType(loginUser.getUserType());
    }

    private void setResultInfo(cn.hmg.zackblog.framework.operatelog.core.pojo.OperateLog operateLogEntity, Object result, Throwable exception, OperateLog operateLog) {
        //设置成功结果
        if (Objects.nonNull(result)) {
            setSuccessResultInfo(operateLogEntity, result, operateLog);
            return;
        }

        //设置异常结果
        if (Objects.nonNull(exception)) {
            setExceptionResultInfo(operateLogEntity, exception);
        }
    }

    private void setSuccessResultInfo(cn.hmg.zackblog.framework.operatelog.core.pojo.OperateLog operateLogEntity, Object result, OperateLog operateLog) {
        if (result instanceof CommonResult<?>) {
            CommonResult<?> commonResult = (CommonResult<?>) result;
            operateLogEntity.setResultCode(commonResult.getCode());
            operateLogEntity.setResultMsg(commonResult.getMessage());
            operateLogEntity.setResultData(operateLog.isRecordResultData() ? JsonUtils.toJsonStr(commonResult.getData()) : "");
        } else {
            //如果结果类型不为CommResult，就手动设置成功结果
            operateLogEntity.setResultCode(SUCCESS.getCode());
            operateLogEntity.setResultMsg(SUCCESS.getMessage());
        }
    }

    private void setMethodInfo(ProceedingJoinPoint joinPoint, cn.hmg.zackblog.framework.operatelog.core.pojo.OperateLog operateLogEntity, OperateLog operateLog) {
        log.info("java method => {}", getMethod(joinPoint).toGenericString());
        operateLogEntity.setJavaMethod(getMethod(joinPoint).toGenericString());

        if (operateLog.recordMethodArgs()) {
            operateLogEntity.setJavaMethodArgs(buildMethodArgs(joinPoint));
        }
    }

    private void setOperateLogAnnotationInfo(cn.hmg.zackblog.framework.operatelog.core.pojo.OperateLog operateLogEntity, ProceedingJoinPoint joinPoint, OperateLog operateLog) {
        operateLogEntity.setName(operateLog.operateName());
        operateLogEntity.setType(operateLog.operateType().getType());


        if (StrUtil.isEmpty(operateLog.moduleName())) {
            //获取tag注解上的name
            //看看注解是否存在
            if (getDeclaringClass(joinPoint).isAnnotationPresent(Tag.class)) {
                Tag tag = getAnnotation(joinPoint, Tag.class);
                operateLogEntity.setModule(tag.name());
            }
            return;
        }
        operateLogEntity.setModule(operateLog.moduleName());
    }

    private void setExceptionResultInfo(cn.hmg.zackblog.framework.operatelog.core.pojo.OperateLog operateLogEntity, Throwable exception) {
        //记录异常信息
        if (exception instanceof BusinessException) {
            log.info("exception => {}", ((BusinessException) exception).getCode());
            BusinessException businessException = (BusinessException) exception;
            //手动设置异常信息，因为如果是java内部异常是无法转换异常信息的，例如算数异常
            operateLogEntity.setResultCode(businessException.getCode());
            operateLogEntity.setResultMsg(businessException.getMessage());
            return;
        }

        //手动设置异常信息，因为异常类型不一定是ServiceException，例如算数异常
        operateLogEntity.setResultCode(GlobalErrorCode.SERVER_ERROR.getCode());
        operateLogEntity.setResultMsg(GlobalErrorCode.SERVER_ERROR.getMessage());
    }


    private String buildMethodArgs(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = getMethodSignature(joinPoint);
        String[] parameterNames = methodSignature.getParameterNames();
        Object[] parameterValues = joinPoint.getArgs();
        Map<String, Object> methodArgsMap = new HashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            String parameterName = parameterNames[i];
            Object parameterValue = parameterValues[i];
            log.info("parameterName => {}，\t parameterValue => {}", parameterName, parameterValue);
            methodArgsMap.put(parameterName, parameterValue);
        }

        return JsonUtils.toJsonStr(methodArgsMap);
    }

    /**
     * 获得注解
     * @param joinPoint 连接点
     * @param annotation 注解class
     * @return 注解
     * @param <T> 泛型
     */
    private <T extends Annotation> T getAnnotation(ProceedingJoinPoint joinPoint, Class<T> annotation) {
        return getDeclaringClass(joinPoint).getAnnotation(annotation);
    }

    private MethodSignature getMethodSignature(ProceedingJoinPoint joinPoint) {
        return ((MethodSignature) joinPoint.getSignature());
    }

    private Method getMethod(ProceedingJoinPoint joinPoint) {
        return getMethodSignature(joinPoint).getMethod();
    }

    private Class<?> getDeclaringClass(ProceedingJoinPoint joinPoint) {
        return getMethodSignature(joinPoint).getMethod().getDeclaringClass();
    }
}
