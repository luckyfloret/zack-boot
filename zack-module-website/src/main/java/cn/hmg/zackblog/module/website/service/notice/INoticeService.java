package cn.hmg.zackblog.module.website.service.notice;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.website.controller.admin.notice.vo.NoticeCreateReqVO;
import cn.hmg.zackblog.module.website.controller.admin.notice.vo.NoticePageReqVO;
import cn.hmg.zackblog.module.website.controller.admin.notice.vo.NoticeUpdateReqVO;
import cn.hmg.zackblog.module.website.controller.admin.notice.vo.NoticeUpdateStatusReqVO;
import cn.hmg.zackblog.module.website.entity.notice.Notice;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 消息管理 服务类
 * </p>
 *
 * @author hmg
 * @since 2023-08-30
 */
public interface INoticeService extends IService<Notice> {

    PageResult<Notice> getPage(NoticePageReqVO reqVO);

    void createNotice(NoticeCreateReqVO reqVO);

    void updateNotice(NoticeUpdateReqVO reqVO);

    void deleteNoticeById(Long id);

    void updateStatus(NoticeUpdateStatusReqVO reqVO);

    Notice getNoticeById(Long id);
}
