package cn.hmg.zackblog.module.website.convert.admin.notice;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.website.controller.admin.notice.vo.*;
import cn.hmg.zackblog.module.website.entity.notice.Notice;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-01 11:42
 * @description:
 */
@Mapper
public interface NoticeConvert {
    NoticeConvert INSTANCE = Mappers.getMapper(NoticeConvert.class);

    PageResult<NoticePageRespVO> convert(PageResult<Notice> pageResult);

    Notice convert(NoticeCreateReqVO reqVO);

    Notice convert(NoticeUpdateReqVO reqVO);

    Notice convert(NoticeUpdateStatusReqVO noticeUpdateStatusReqVO);

    NoticeRespVO convert(Notice notice);
}
