package cn.hmg.zackblog.module.website.service.friendlink;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.website.controller.admin.friendlink.vo.FriendLinkApprovalRejectReqVO;
import cn.hmg.zackblog.module.website.controller.admin.friendlink.vo.FriendLinkCreateReqVO;
import cn.hmg.zackblog.module.website.controller.admin.friendlink.vo.FriendLinkPageReqVO;
import cn.hmg.zackblog.module.website.controller.admin.friendlink.vo.FriendLinkUpdateReqVO;
import cn.hmg.zackblog.module.website.entity.friendlink.FriendLink;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 友情链接 服务类
 * </p>
 *
 * @author hmg
 * @since 2023-08-30
 */
public interface IFriendLinkService extends IService<FriendLink> {
    /**
     * 获取友情链接分页列表
     * @param reqVO 请求参数
     * @return 友情链接分页列表
     */
    PageResult<FriendLink> getPage(FriendLinkPageReqVO reqVO);

    /**
     * 创建友情链接
     * @param reqVO 请求参数
     */
    void createFriendLink(FriendLinkCreateReqVO reqVO);

    /**
     * 更新友情链接
     * @param reqVO 请求参数
     */
    void updateFriendLink(FriendLinkUpdateReqVO reqVO);

    /**
     * 根据id删除友情链接
     * @param id 友情链接id
     */
    void deleteFriendLinkById(Long id);

    /**
     * 根据id获取友情链接
     * @param id 友情链接id
     * @return FriendLink
     */
    FriendLink getFriendLinkById(Long id);

    /**
     * 友情链接审批通过
     * @param id 友情链接id
     */
    void approvalPassFriendLink(Long id);

    /**
     * 友情链接审批拒绝
     * @param reqVO 请求参数
     */
    void approvalRejectFriendLink(FriendLinkApprovalRejectReqVO reqVO);
}
