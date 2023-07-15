package cn.hmg.zackblog.module.system.convert.auth;

import cn.hmg.zackblog.framework.core.pojo.LoginUser;
import cn.hmg.zackblog.module.system.controller.admin.auth.vo.AdminAuthLoginRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-15 15:52
 * @description: admin 认证转换类
 */
@Mapper
public interface AdminAuthConvert {
    AdminAuthConvert INSTANCE = Mappers.getMapper(AdminAuthConvert.class);


    /**
     *
     * @param loginUser 登录用户
     * @return  AdminAuthLoginRespVO
     */
    @Mapping(target = "expireTime", source = "refreshTokenExpireTime")
    AdminAuthLoginRespVO convert(LoginUser loginUser);
}
