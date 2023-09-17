package cn.hmg.zackblog.module.website.service.friendlink;

import cn.hmg.zackblog.framework.common.exception.BusinessException;
import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.website.controller.admin.friendlink.vo.FriendLinkApprovalRejectReqVO;
import cn.hmg.zackblog.module.website.controller.admin.friendlink.vo.FriendLinkCreateReqVO;
import cn.hmg.zackblog.module.website.controller.admin.friendlink.vo.FriendLinkPageReqVO;
import cn.hmg.zackblog.module.website.controller.admin.friendlink.vo.FriendLinkUpdateReqVO;
import cn.hmg.zackblog.module.website.convert.admin.friendlink.FriendLinkConvert;
import cn.hmg.zackblog.module.website.entity.friendlink.FriendLink;
import cn.hmg.zackblog.module.website.mapper.friendlink.FriendLinkMapper;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Objects;

import static cn.hmg.zackblog.module.website.enums.ApprovalStatusEnum.*;
import static cn.hmg.zackblog.module.website.enums.ErrorCodeEnum.*;

/**
 * <p>
 * 友情链接 服务实现类
 * </p>
 *
 * @author hmg
 * @since 2023-08-30
 */
@Service
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkMapper, FriendLink> implements IFriendLinkService {
    @Resource
    private FriendLinkMapper friendLinkMapper;

    @Override
    public PageResult<FriendLink> getPage(FriendLinkPageReqVO reqVO) {
        return friendLinkMapper.getPage(reqVO);
    }

    @Override
    public void createFriendLink(FriendLinkCreateReqVO reqVO) {
        //校验邮箱和网站地址是否唯一
        verifyFieldIsUnique(null, reqVO.getEmail(), reqVO.getWebsiteUrl());

        //新增友情链接
        friendLinkMapper.insert(FriendLinkConvert.INSTANCE.convert(reqVO).setApprovalStatus(WAIT_REVIEW.getStatus()));
    }

    @Override
    public void updateFriendLink(FriendLinkUpdateReqVO reqVO) {
        verifyFriendLinkExists(reqVO.getId());

        verifyFieldIsUnique(reqVO.getId(), reqVO.getEmail(), reqVO.getWebsiteUrl());

        //更新友情链接
        friendLinkMapper.updateById(FriendLinkConvert.INSTANCE.convert(reqVO));
    }

    @Override
    public void deleteFriendLinkById(Long id) {
        //校验友情链接是否存在
        verifyFriendLinkExists(id);

        //执行删除
        friendLinkMapper.deleteById(id);
    }

    @Override
    public FriendLink getFriendLinkById(Long id) {
        return verifyFriendLinkExists(id);
    }

    @Override
    public void approvalPassFriendLink(Long id) {
        //校验友情链接是否存在
        FriendLink friendLink = verifyFriendLinkExists(id);
        //校验审批状态
        verifyApprovalStatus(friendLink.getApprovalStatus());
        friendLinkMapper.updateById(friendLink.setApprovalStatus(PASS.getStatus()));
    }

    @Override
    public void approvalRejectFriendLink(FriendLinkApprovalRejectReqVO reqVO) {
        //校验友情链接是否存在
        FriendLink friendLink = verifyFriendLinkExists(reqVO.getId());
        //校验审批状态
        verifyApprovalStatus(friendLink.getApprovalStatus());

        friendLink.setApprovalStatus(REJECT.getStatus());
        friendLink.setRejectOpinion(reqVO.getRejectOpinion());
        friendLinkMapper.updateById(friendLink);
    }

    /**
     * 校验友情链接审批状态
     * @param approvalStatus 审批状态
     */
    private void verifyApprovalStatus(Integer approvalStatus) {
        if (!WAIT_REVIEW.getStatus().equals(approvalStatus)) {
            throw new BusinessException(FRIEND_LINK_HAS_APPROVED.getCode(), FRIEND_LINK_HAS_APPROVED.getMessage());
        }
    }

    /**
     * 校验友情链接是否存在
     * @param id 友情链接id
     * @return FriendLink
     */
    private FriendLink verifyFriendLinkExists(Long id) {
        FriendLink friendLink = friendLinkMapper.selectById(id);
        if (Objects.isNull(friendLink)) {
            throw new BusinessException(FRIEND_LINK_NOT_EXISTS.getCode(), FRIEND_LINK_NOT_EXISTS.getMessage());
        }

        return friendLink;
    }

    /**
     * 校验字段唯一性
     * @param id 友情链接id
     * @param email 邮箱
     * @param websiteUrl 网站url
     */
    private void verifyFieldIsUnique(Long id, String email, String websiteUrl) {
        //校验邮箱唯一性
        verifyEmailIsUnique(id, email);
        //校验网站url唯一性
        verifyWebSiteUrlIsUnique(id, websiteUrl);
    }

    /**
     * 校验网站url是否唯一
     * @param id 友情链接id
     * @param websiteUrl 网站url
     */
    private void verifyWebSiteUrlIsUnique(Long id, String websiteUrl) {
        FriendLink friendLink = friendLinkMapper.getFriendLinkByWebsiteUrl(websiteUrl);

        if (Objects.isNull(friendLink)) {
            return;
        }

        Assert.isTrue(friendLink.getId().equals(id),
                () -> new BusinessException(FRIEND_LINK_BINDING_WEBSITE_URL_ALREADY_EXISTS.getCode(),
                        FRIEND_LINK_BINDING_WEBSITE_URL_ALREADY_EXISTS.getMessage()));
    }

    /**
     * 校验邮箱是否唯一
     * @param id 友情链接id
     * @param email 邮箱
     */
    private void verifyEmailIsUnique(Long id, String email) {
        FriendLink friendLink = friendLinkMapper.getFriendLinkByEmail(email);

        if (Objects.isNull(friendLink)) {
            return;
        }

        Assert.isTrue(friendLink.getId().equals(id),
                () -> new BusinessException(FRIEND_LINK_BINDING_EMAIL_ALREADY_EXISTS.getCode(),
                        FRIEND_LINK_BINDING_EMAIL_ALREADY_EXISTS.getMessage()));
    }
}
