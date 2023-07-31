package cn.hmg.zackblog.module.system.mapper.user;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.mybatisplus.core.mapper.BaseMapperExtend;
import cn.hmg.zackblog.framework.mybatisplus.core.query.LambdaQueryWrapperExtend;
import cn.hmg.zackblog.module.system.controller.admin.user.vo.UserPageReqVO;
import cn.hmg.zackblog.module.system.entity.user.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户管理 Mapper 接口
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Mapper
public interface UserMapper extends BaseMapperExtend<User> {

    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return User
     */
    default User selectByUsername(String username){
        return selectOne(User::getUsername, username);
    };

    default PageResult<User> getPage(UserPageReqVO userPageReqVO) {
        return page(userPageReqVO, new LambdaQueryWrapperExtend<User>()
                .likeIfExists(User::getUsername, userPageReqVO.getUsername())
                .eqIfExists(User::getType, userPageReqVO.getType())
                .likeIfExists(User::getEmail, userPageReqVO.getEmail())
                .likeIfExists(User::getMobile, userPageReqVO.getMobile())
                .eqIfExists(User::getStatus, userPageReqVO.getStatus())
        );
    }

    default User selectByEmail(String email) {
        return selectOne(User::getEmail, email);
    }

    default User selectByMobile(String mobile) {
        return selectOne(User::getMobile, mobile);
    }
}
