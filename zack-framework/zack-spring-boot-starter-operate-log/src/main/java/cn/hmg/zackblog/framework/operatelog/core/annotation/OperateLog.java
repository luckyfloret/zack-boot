package cn.hmg.zackblog.framework.operatelog.core.annotation;

import cn.hmg.zackblog.framework.operatelog.core.enums.OperateLogTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-29 22:32
 * @description: 记录操作日志
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OperateLog {
    /**
     * 模块名
     * @return 模块名
     */
    String moduleName() default "";

    /**
     * 操作名
     * @return 操作名
     */
    String operateName() default "";

    /**
     * 操作类型，必须指定
     * @return 操作类型
     */
    OperateLogTypeEnum operateType();

    /**
     * 是否开启日志记录，默认开启
     * @return true or false
     */
    boolean enable() default true;

    /**
     * 是否记录方法参数， 默认开启
     */
    boolean recordMethodArgs() default true;

    /**
     * 是否记录返回结果数据，默认关闭
     * @return true or false
     */
    boolean isRecordResultData() default false;
}
