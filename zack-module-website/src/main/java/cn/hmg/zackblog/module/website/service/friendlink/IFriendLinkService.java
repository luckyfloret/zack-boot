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

    PageResult<FriendLink> getPage(FriendLinkPageReqVO reqVO);

    void createFriendLink(FriendLinkCreateReqVO reqVO);

    void updateFriendLink(FriendLinkUpdateReqVO reqVO);

    void deleteFriendLinkById(Long id);

    FriendLink getFriendLinkById(Long id);

    void approvalPassFriendLink(Long id);

    void approvalRejectFriendLink(FriendLinkApprovalRejectReqVO reqVO);
}
