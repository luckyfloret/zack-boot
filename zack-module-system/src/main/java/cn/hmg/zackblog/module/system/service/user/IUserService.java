package cn.hmg.zackblog.module.system.service.user;

import cn.hmg.zackblog.common.pojo.PageResult;
import cn.hmg.zackblog.module.system.controller.admin.user.vo.*;
import cn.hmg.zackblog.module.system.entity.user.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Optional;

/**
 * <p>
 * 用户管理 服务类
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
public interface IUserService extends IService<User> {

    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return User
     */
    Optional<User> getUserByUsername(String username);

    /**
     * 密码匹配
     * @param password 未加密密码
     * @param encodePassword 加密密码
     * @return true or false
     */
    boolean ifPasswordMatch(String password, String encodePassword);

    /**
     * 根据用户id获取用户信息
     * @param userId 用户id
     * @return User
     */
    Optional<User> getUserById(Long userId);

    PageResult<UserPageRespVO> getPage(UserPageReqVO userPageReqVO);

    void createUser(UserCreateReqVO userCreateReqVO);

    void updateUser(UserUpdateReqVO userUpdateReqVO);

    void deleteById(Long id);

    void resetPassword(UserResetPasswordReqVO userResetPasswordReqVO);

    void verifyUserIsExistsByUserId(Long userId);
}
