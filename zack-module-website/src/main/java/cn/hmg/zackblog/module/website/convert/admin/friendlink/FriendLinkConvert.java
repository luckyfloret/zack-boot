package cn.hmg.zackblog.module.website.convert.admin.friendlink;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.website.controller.admin.friendlink.vo.FriendLinkCreateReqVO;
import cn.hmg.zackblog.module.website.controller.admin.friendlink.vo.FriendLinkPageRespVO;
import cn.hmg.zackblog.module.website.controller.admin.friendlink.vo.FriendLinkRespVO;
import cn.hmg.zackblog.module.website.controller.admin.friendlink.vo.FriendLinkUpdateReqVO;
import cn.hmg.zackblog.module.website.entity.friendlink.FriendLink;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-30 14:30
 * @description:
 */
@Mapper
public interface FriendLinkConvert {
    FriendLinkConvert INSTANCE = Mappers.getMapper(FriendLinkConvert.class);

    PageResult<FriendLinkPageRespVO> convert(PageResult<FriendLink> pageResult);

    FriendLink convert(FriendLinkCreateReqVO reqVO);

    FriendLink convert(FriendLinkUpdateReqVO reqVO);

    FriendLinkRespVO convert(FriendLink friendLink);
}
