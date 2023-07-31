package cn.hmg.zackblog.module.system.convert.user;

import cn.hmg.zackblog.framework.security.core.pojo.UserDetails;
import cn.hmg.zackblog.module.system.controller.admin.user.vo.*;
import cn.hmg.zackblog.module.system.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-17 0:21
 * @description: 用户convert 接口
 */
@Mapper
public interface UserConvert {
    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    /**
     * User转换为UserDetails
     * @param user user对象
     * @return UserDetails
     */
    UserDetails convertToUserDetails(User user);

    List<UserPageRespVO> convert(List<User> user);

    User convert(UserCreateReqVO userCreateReqVO);

    User convert(UserUpdateReqVO userUpdateReqVO);

    UserRespVO convert(User user);
}
