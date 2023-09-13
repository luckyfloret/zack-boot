package cn.hmg.zackblog.module.website.mapper.friendlink;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.mybatisplus.core.mapper.BaseMapperExtend;
import cn.hmg.zackblog.framework.mybatisplus.core.query.LambdaQueryWrapperExtend;
import cn.hmg.zackblog.module.website.controller.admin.friendlink.vo.FriendLinkPageReqVO;
import cn.hmg.zackblog.module.website.entity.friendlink.FriendLink;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 友情链接 Mapper 接口
 * </p>
 *
 * @author hmg
 * @since 2023-08-30
 */
@Mapper
public interface FriendLinkMapper extends BaseMapperExtend<FriendLink> {

    default PageResult<FriendLink> getPage(FriendLinkPageReqVO reqVO) {
        return page(reqVO, new LambdaQueryWrapperExtend<FriendLink>()
                .likeIfExists(FriendLink::getName, reqVO.getName())
                .likeIfExists(FriendLink::getEmail, reqVO.getEmail())
                .eqIfExists(FriendLink::getApprovalStatus, reqVO.getApprovalStatus())
                .orderByAsc(FriendLink::getSort)
        );
    }

    default FriendLink getFriendLinkByEmail(String email) {
        return selectOne(FriendLink::getEmail, email);
    }

    default FriendLink getFriendLinkByWebsiteUrl(String websiteUrl) {
        return selectOne(FriendLink::getWebsiteUrl, websiteUrl);
    }
}
