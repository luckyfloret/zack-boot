package cn.hmg.zackblog.module.system.service.permission;

import cn.hmg.zackblog.common.exception.ServiceException;
import cn.hmg.zackblog.common.pojo.PageResult;
import cn.hmg.zackblog.common.utils.collections.CollectionUtils;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.role.RoleCreateReqVO;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.role.RolePageReqVO;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.role.RolePageRespVO;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.role.RoleUpdateReqVO;
import cn.hmg.zackblog.module.system.convert.permission.RoleConvert;
import cn.hmg.zackblog.module.system.entity.permission.Role;
import cn.hmg.zackblog.module.system.enums.RoleCodeEnum;
import cn.hmg.zackblog.module.system.enums.RoleTypeEnum;
import cn.hmg.zackblog.module.system.mapper.permission.RoleMapper;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

import static cn.hmg.zackblog.module.system.enums.ErrorCodeEnum.*;
import static cn.hmg.zackblog.common.enums.CommonStatusEnum.*;

/**
 * <p>
 * 角色管理 服务实现类
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Slf4j
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    /**
     * 角色信息的缓存
     * 使用volatile关键字修饰是为了在高并发场景下保证变量的可见性，有更新立即从主存中读取
     */
    @Getter
    private volatile Map<Long, Role> roleCache;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    @Lazy //懒加载， 避免循环依赖报错
    private PermissionService permissionService;


    private void initRoleCache() {
        List<Role> roles = roleMapper.selectList();
        log.info("[initRoleCache] => 初始化角色信息缓存，数量为：{}", roles.size());
        roleCache = CollectionUtils.convertMap(roles, Role::getId);
    }

    @PostConstruct
    @Override
    public void initLocalCache() {
        initRoleCache();
    }

    @Override
    public Role getRoleByIdFromCache(Long roleId) {
        return roleCache.get(roleId);
    }

    @Override
    public PageResult<RolePageRespVO> getPage(RolePageReqVO rolePageReqVO) {
        PageResult<Role> page = roleMapper.getPage(rolePageReqVO);
        return new PageResult<>(RoleConvert.INSTANCE.convert(page.getData()), page.getTotal());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createRole(RoleCreateReqVO roleCreateReqVO) {
        //校验部分角色信息是否唯一
        verifyRoleInfo(roleCreateReqVO.getName(), roleCreateReqVO.getCode(), roleCreateReqVO.getStatus(), null);

        //插入db
        Role role = RoleConvert.INSTANCE.convert(roleCreateReqVO);
        role.setType(RoleTypeEnum.CUSTOM.getType());
        roleMapper.insert(role);

        //TODO 通知MQ发送刷新缓存消息

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateRole(RoleUpdateReqVO roleUpdateReqVO) {
        //校验是否是内置角色
        verifyRoleType(roleUpdateReqVO.getId());

        //校验部分角色信息是否唯一
        verifyRoleInfo(roleUpdateReqVO.getName(), roleUpdateReqVO.getCode(), roleUpdateReqVO.getStatus(), roleUpdateReqVO.getId());

        //插入db
        Role role = RoleConvert.INSTANCE.convert(roleUpdateReqVO);
        roleMapper.updateById(role);

        //TODO 通知MQ发送刷新缓存消息
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteRoleById(Long id) {
        //校验角色类型
        verifyRoleType(id);

        //删除与角色相关联的用户与菜单列表
        permissionService.deleteRoleAssociation(id);
        roleMapper.deleteById(id);

        //TODO 通知MQ发送刷新缓存消息
    }

    @Override
    public Role getRoleById(Long roleId) {
        return roleMapper.selectOne(roleId);
    }

    @Override
    public Role getSuperAdminRole(String code) {
        return roleMapper.selectOne(code);
    }

    @Override
    public List<Role> getRoleListFromDbByStatus(Integer status) {
        return roleMapper.selectListByStatus(status);
    }

    /**
     * 校验角色类型，不能操作系统内置角色
     *
     * @param roleId 角色id
     */
    private void verifyRoleType(Long roleId) {
        Role role = roleMapper.selectById(roleId);
        //角色不存在
        Assert.notNull(role, () -> new ServiceException(ROLE_NOT_EXISTS.getCode(), ROLE_NOT_EXISTS.getMessage()));

        //不能操作系统内置角色
        Assert.isFalse(role.getType().equals(RoleTypeEnum.SYSTEM.getType()),
                () -> new ServiceException(ROLE_CANNOT_OPERATE_SYSTEM_ROLE.getCode(), ROLE_CANNOT_OPERATE_SYSTEM_ROLE.getMessage()));
    }

    /**
     * 校验部分角色信息是否唯一、角色状态是否合法等
     *
     * @param roleName 角色名称
     * @param code     角色编码
     * @param status   角色状态
     * @param roleId   角色id
     */
    private void verifyRoleInfo(String roleName, String code, Integer status, Long roleId) {
        //判断角色编码是否是超管与普通用户，如果是直接抛出异常（因为是内置角色， 所以超管只能query， 普通用户只能query、assignPermission）
        Assert.isFalse(RoleCodeEnum.isSuperAdmin(code) || RoleCodeEnum.isNormal(code),
                () -> new ServiceException(ROLE_CODE_CANNOT_USE.getCode(),
                        ROLE_CODE_CANNOT_USE.getMessage()));

        //校验角色状态（只能是开启或关闭状态）
        Set<Integer> statusSet = CollectionUtils.asSet(ENABLED.getStatusCode(), DISABLED.getStatusCode());
        Assert.isTrue(statusSet.contains(status), () -> new ServiceException(ROLE_STATUS_ERROR.getCode(), ROLE_STATUS_ERROR.getMessage()));

        //根据角色id查询角色信息
        Role role = roleMapper.selectByRoleName(roleName);

        //如果为null表示角色不存在，并且是新增操作，直接结束方法即可
        if (Objects.isNull(role)) {
            return;
        }

        //如果查询到角色，roleId为null的话，表示角色已存在，并且是新增操作，而不是更新操作，所以直接断言抛出异常即可
        Assert.notNull(roleId, () -> new ServiceException(ROLE_NAME_ALREADY_EXISTS.getCode(), ROLE_NAME_ALREADY_EXISTS.getMessage()));

        //如果是更新操作，id必须是相同的，不然就是角色名相同了
        Assert.isTrue(roleId.equals(role.getId()), () -> new ServiceException(ROLE_NAME_ALREADY_EXISTS.getCode(), ROLE_NAME_ALREADY_EXISTS.getMessage()));
    }


}
