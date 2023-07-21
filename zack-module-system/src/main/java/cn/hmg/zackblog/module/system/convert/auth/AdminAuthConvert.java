package cn.hmg.zackblog.module.system.convert.auth;

import cn.hmg.zackblog.framework.core.pojo.LoginUser;
import cn.hmg.zackblog.module.system.controller.admin.auth.vo.AdminAuthLoginRespVO;
import cn.hmg.zackblog.module.system.controller.admin.auth.vo.AdminAuthMenuRespVO;
import cn.hmg.zackblog.module.system.entity.permission.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Comparator;
import java.util.List;

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

    default AdminAuthMenuRespVO buildMenuTree(List<Menu> menuListFromCache){
        menuListFromCache.sort(Comparator.comparing(Menu::getSort));

        return null;
    }
}
