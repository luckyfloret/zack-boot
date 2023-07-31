package cn.hmg.zackblog.framework.security.core.service;

import cn.hmg.zackblog.framework.security.core.pojo.UserDetails;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-17 0:03
 * @description: 用户服务
 */
public interface SecurityUserService {
    /**
     * 根据用户id获取用户详情
     * @param userId 用户id
     * @return UserDetails
     */
    UserDetails getUserDetailsByUserId(Long userId);
}
