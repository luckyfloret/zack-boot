package cn.hmg.zackblog.module.system.service.user;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.system.controller.admin.user.vo.*;
import cn.hmg.zackblog.module.system.controller.admin.user.vo.center.UserCenterUpdateReqVO;
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

    /**
     * 用户分页
     * @param userPageReqVO 用户分页 request vo
     * @return PageResult<UserPageRespVO>
     */
    PageResult<UserPageRespVO> getPage(UserPageReqVO userPageReqVO);

    /**
     * 创建用户
     * @param userCreateReqVO 创建用户 request vo
     */
    void createUser(UserCreateReqVO userCreateReqVO);

    /**
     * 更新用户
     * @param userUpdateReqVO 更新用户 request vo
     */
    void updateUser(UserUpdateReqVO userUpdateReqVO);

    /**
     * 根据id删除用户
     * @param id 用户id
     */
    void deleteById(Long id);

    /**
     * 重置密码
     * @param userResetPasswordReqVO 重置密码 request vo
     */
    void resetPassword(UserResetPasswordReqVO userResetPasswordReqVO);

    /**
     * 根据用户id校验用户是否存在
     * @param userId 用户id
     */
    void verifyUserIsExistsByUserId(Long userId);

    void updateUserPassword(Long userId, UserUpdatePasswordReqVO userUpdatePasswordReqVO);

    void updateUserPersonalInfo(Long loginUserId, UserCenterUpdateReqVO userCenterUpdateReqVO);
}
