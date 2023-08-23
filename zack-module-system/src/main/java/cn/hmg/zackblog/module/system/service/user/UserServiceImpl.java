package cn.hmg.zackblog.module.system.service.user;

import cn.hmg.zackblog.framework.common.exception.BusinessException;
import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.common.utils.collections.CollectionUtils;
import cn.hmg.zackblog.module.system.controller.admin.user.vo.*;
import cn.hmg.zackblog.module.system.controller.admin.user.vo.center.UserCenterUpdateReqVO;
import cn.hmg.zackblog.module.system.convert.user.UserConvert;
import cn.hmg.zackblog.module.system.entity.user.User;
import cn.hmg.zackblog.module.system.mapper.user.UserMapper;
import cn.hmg.zackblog.module.system.service.permission.PermissionService;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static cn.hmg.zackblog.module.system.constants.CommonConstant.DEFAULT_AVATAR_URL;
import static cn.hmg.zackblog.module.system.constants.CommonConstant.DEFAULT_PASSWORD;
import static cn.hmg.zackblog.module.system.enums.ErrorCodeEnum.*;
import static cn.hmg.zackblog.framework.common.enums.CommonStatusEnum.*;
import static cn.hmg.zackblog.framework.common.enums.UserSexEnum.*;
import static cn.hmg.zackblog.framework.common.enums.UserTypeEnum.*;

/**
 * <p>
 * 用户管理 服务实现类
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Resource
    @Lazy //避免循环依赖报错
    private PermissionService permissionService;

    @Override
    public Optional<User> getUserByUsername(String username) {
        return Optional.ofNullable(userMapper.selectByUsername(username));
    }

    @Override
    public boolean ifPasswordMatch(String password, String encodePassword) {
        return passwordEncoder.matches(password, encodePassword);
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return Optional.ofNullable(userMapper.selectById(userId));
    }

    @Override
    public PageResult<UserPageRespVO> getPage(UserPageReqVO userPageReqVO) {
        PageResult<User> pageResult = userMapper.getPage(userPageReqVO);
        return UserConvert.INSTANCE.convert(pageResult);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createUser(UserCreateReqVO userCreateReqVO) {
        //校验用户信息是否符合系统要求
        verifyUser(null, userCreateReqVO.getUsername(), userCreateReqVO.getEmail(),
                userCreateReqVO.getMobile(), userCreateReqVO.getStatus(),
                userCreateReqVO.getType(), userCreateReqVO.getSex());

        User user = UserConvert.INSTANCE.convert(userCreateReqVO);
        //设置用户默认值
        setUserDefaultValue(user);

        //插入db
        userMapper.insert(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUser(UserUpdateReqVO userUpdateReqVO) {
        //校验用户是否存在
        verifyUserIsExistsById(userUpdateReqVO.getId());

        //校验用户信息
        verifyUser(userUpdateReqVO.getId(), userUpdateReqVO.getUsername(), userUpdateReqVO.getEmail(),
                userUpdateReqVO.getMobile(), userUpdateReqVO.getStatus(), null, userUpdateReqVO.getSex());

        //插入db
        userMapper.updateById(UserConvert.INSTANCE.convert(userUpdateReqVO));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Long id) {
        //校验用户是否存在
        verifyUserIsExistsById(id);

        //删除与用户关联的角色信息
        permissionService.deleteUserRoleAssociation(id);

        //删除用户
        userMapper.deleteById(id);
    }

    @Override
    public void resetPassword(UserResetPasswordReqVO userResetPasswordReqVO) {
        //校验用户是否存在
        User user = userMapper.selectById(userResetPasswordReqVO.getUserId());
        Assert.notNull(user, () -> new BusinessException(USER_NOT_EXISTS.getCode(), USER_NOT_EXISTS.getMessage()));
        user.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
        //更新用户信息
        userMapper.updateById(user);
    }

    @Override
    public void verifyUserIsExistsByUserId(Long userId) {
        verifyUserIsExistsById(userId);
    }

    @Override
    public void updateUserPassword(Long userId, UserUpdatePasswordReqVO userUpdatePasswordReqVO) {
        //校验用户是否存在
        User user = userMapper.selectById(userId);
        Assert.notNull(user, () -> new BusinessException(USER_NOT_EXISTS.getCode(), USER_NOT_EXISTS.getMessage()));

        //对比旧密码和原密码
        Assert.isTrue(passwordEncoder.matches(userUpdatePasswordReqVO.getOldPassword(), user.getPassword()),
                () -> new BusinessException(USER_OLD_PASSWORD_ERROR.getCode(), USER_OLD_PASSWORD_ERROR.getMessage()));

        //更新密码
        user.setPassword(passwordEncoder.encode(userUpdatePasswordReqVO.getNewPassword()));
        userMapper.updateById(user);
    }

    @Override
    public void updateUserPersonalInfo(Long loginUserId, UserCenterUpdateReqVO userCenterUpdateReqVO) {
        //校验用户是否存在
        verifyUserIsExistsById(loginUserId);
        //校验邮箱是否唯一
        verifyEmailIsUnique(loginUserId, userCenterUpdateReqVO.getEmail());
        //校验手机号是否唯一
        verifyMobileIsUnique(loginUserId, userCenterUpdateReqVO.getMobile());
        //更新用户信息
        userMapper.updateById(UserConvert.INSTANCE.convert(userCenterUpdateReqVO).setId(loginUserId));
    }

    /**
     * 校验用户是否存在
     *
     * @param userId 用户id
     */
    private void verifyUserIsExistsById(Long userId) {
        User user = userMapper.selectById(userId);
        Assert.notNull(user, () -> new BusinessException(USER_NOT_EXISTS.getCode(), USER_NOT_EXISTS.getMessage()));
    }

    /**
     * 设置用户默认值
     *
     * @param user 用户
     */
    private void setUserDefaultValue(User user) {
        user.setAvatar(DEFAULT_AVATAR_URL);
        user.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
    }


    /**
     * 校验用户信息， create update时使用
     *
     * @param userId   用户id
     * @param username 用户名
     * @param email    邮箱
     * @param mobile   手机号
     * @param status   用户状态
     * @param userType 用户类型
     * @param sex      性别
     */
    private void verifyUser(Long userId, String username, String email, String mobile, Integer status, Integer userType, Integer sex) {
        //校验用户名是否存在
        verifyUsernameIsExists(userId, username);

        //校验邮箱是否唯一
        verifyEmailIsUnique(userId, email);

        //校验手机号是否唯一
        verifyMobileIsUnique(userId, mobile);

        //校验用户状态
        Set<Integer> userStatusSet = CollectionUtils.asSet(ENABLED.getStatusCode(), DISABLED.getStatusCode());
        Assert.isTrue(userStatusSet.contains(status),
                () -> new BusinessException(USER_STATUS_ERROR.getCode(), USER_STATUS_ERROR.getMessage()));

        //用户性别不为null时才进行校验，因为更新用户信息不需要用到此属性
        if (Objects.nonNull(userType)) {
            //校验用户类型
            Set<Integer> userTypeSet = CollectionUtils.asSet(FRONT_USER.getType(), ADMIN_USER.getType());
            Assert.isTrue(userTypeSet.contains(userType),
                    () -> new BusinessException(USER_TYPE_ERROR.getCode(), USER_TYPE_ERROR.getMessage()));
        }


        //校验用户性别
        Set<Integer> sexSet = CollectionUtils.asSet(MAN.getSex(), WOMEN.getSex());
        Assert.isTrue(sexSet.contains(sex),
                () -> new BusinessException(USER_SEX_ERROR.getCode(), USER_SEX_ERROR.getMessage()));

    }

    /**
     * 校验用户名是否存在
     *
     * @param userId   用户id
     * @param username 用户名
     */
    private void verifyUsernameIsExists(Long userId, String username) {
        //根据用户名查询用户
        User user = userMapper.selectByUsername(username);

        if (Objects.isNull(user)) {
            return;
        }

        //如果用户id为null，默认为新增操作，说明用户名称有相同的
        Assert.notNull(userId, () -> new BusinessException(USER_USERNAME_EXISTS.getCode(), USER_USERNAME_EXISTS.getMessage()));

        //id不一致，但是id不为null，默认是更新操作，说明更新时用户名称有相同的
        Assert.isTrue(user.getId().equals(userId), () -> new BusinessException(USER_USERNAME_EXISTS.getCode(), USER_USERNAME_EXISTS.getMessage()));
    }

    /**
     * 校验邮箱是否是唯一的
     *
     * @param userId 用户id
     * @param email  用户邮箱
     */
    private void verifyEmailIsUnique(Long userId, String email) {
        //根据邮箱查询用户
        User user = userMapper.selectByEmail(email);

        if (Objects.isNull(user)) {
            return;
        }
        //如果用户id为null，默认为新增操作，说明用户邮箱存在相同的
        Assert.notNull(userId, () -> new BusinessException(USER_EMAIL_EXISTS.getCode(), USER_EMAIL_EXISTS.getMessage()));

        //用户id不为null，默认为更新操作，如果与db出来的id不同，说明用户邮箱存在相同的
        Assert.isTrue(user.getId().equals(userId), () -> new BusinessException(USER_EMAIL_EXISTS.getCode(), USER_EMAIL_EXISTS.getMessage()));
    }

    /**
     * 校验手机号是否唯一
     *
     * @param userId 用户id
     * @param mobile 手机号
     */
    private void verifyMobileIsUnique(Long userId, String mobile) {
        //根据手机号查询用户
        User user = userMapper.selectByMobile(mobile);

        //如果不存在直接结束方法
        if (Objects.isNull(user)) {
            return;
        }

        //如果用户id为null，默认为新增操作，说明用户手机号存在相同的
        Assert.notNull(userId, () -> new BusinessException(USER_MOBILE_EXISTS.getCode(), USER_MOBILE_EXISTS.getMessage()));

        //用户id不为null，默认为更新操作，如果与db出来的id不同，说明用户手机号存在相同的
        Assert.isTrue(user.getId().equals(userId), () -> new BusinessException(USER_MOBILE_EXISTS.getCode(), USER_MOBILE_EXISTS.getMessage()));
    }
}
