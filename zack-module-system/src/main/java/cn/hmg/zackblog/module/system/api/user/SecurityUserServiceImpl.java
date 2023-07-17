package cn.hmg.zackblog.module.system.api.user;

import cn.hmg.zackblog.common.exception.ServiceException;
import cn.hmg.zackblog.framework.core.pojo.UserDetails;
import cn.hmg.zackblog.framework.core.service.SecurityUserService;
import cn.hmg.zackblog.module.system.convert.user.UserConvert;
import cn.hmg.zackblog.module.system.entity.user.User;
import cn.hmg.zackblog.module.system.service.user.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

import static cn.hmg.zackblog.module.system.enums.ErrorCodeEnum.USER_NOT_EXISTS;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-17 0:17
 * @description: SecurityUserService实现类
 */
@Service
public class SecurityUserServiceImpl implements SecurityUserService {
    @Resource
    private IUserService userService;

    @Override
    public UserDetails getUserDetailsByUserId(Long userId) {
        User user = userService.getById(userId);
        if (Objects.isNull(user)) {
            throw new ServiceException(USER_NOT_EXISTS.getCode(), USER_NOT_EXISTS.getMessage());
        }
        return UserConvert.INSTANCE.convertToUserDetails(user);
    }
}
