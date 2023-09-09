package cn.hmg.zackblog.module.website.service;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.website.controller.admin.vo.notice.NoticeCreateReqVO;
import cn.hmg.zackblog.module.website.controller.admin.vo.notice.NoticePageReqVO;
import cn.hmg.zackblog.module.website.controller.admin.vo.notice.NoticeUpdateReqVO;
import cn.hmg.zackblog.module.website.controller.admin.vo.notice.NoticeUpdateStatusReqVO;
import cn.hmg.zackblog.module.website.entity.Notice;
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
