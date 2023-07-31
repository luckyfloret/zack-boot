package cn.hmg.zackblog.module.system.service.permission;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.role.RoleCreateReqVO;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.role.RolePageReqVO;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.role.RolePageRespVO;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.role.RoleUpdateReqVO;
import cn.hmg.zackblog.module.system.entity.permission.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色管理 服务类
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
public interface IRoleService extends IService<Role> {

    /**
     * 初始化本地缓存
     */
    void initLocalCache();

    /**
     * 根据角色id从缓存中获取角色信息
     * @param roleId 角色id
     * @return Role
     */
    Role getRoleByIdFromCache(Long roleId);

    /**
     * 角色分页
     * @param rolePageReqVO 角色分页 request vo
     * @return PageResult<RolePageRespVO>
     */
    PageResult<RolePageRespVO> getPage(RolePageReqVO rolePageReqVO);

    /**
     * 创建角色
     * @param roleCreateReqVO 角色创建 request vo
     */
    void createRole(RoleCreateReqVO roleCreateReqVO);

    /**
     * 更新角色信息
     * @param roleUpdateReqVO 更新角色 request vo
     */
    void updateRole(RoleUpdateReqVO roleUpdateReqVO);

    /**
     * 根据id删除角色
     * @param id 角色id
     */
    void deleteRoleById(Long id);

    /**
     * 根据id查询角色信息
     * @param roleId 角色id
     * @return Role
     */
    Role getRoleById(Long roleId);

    /**
     * 根据角色权限编码获取超级管理员角色
     * @param code 角色权限编码
     * @return Role
     */
    Role getSuperAdminRole(String code);

    /**
     * 根据角色状态获取角色列表
     * @param status 角色状态
     * @return 角色列表
     */
   List<Role> getRoleListFromDbByStatus(Integer status);
}
