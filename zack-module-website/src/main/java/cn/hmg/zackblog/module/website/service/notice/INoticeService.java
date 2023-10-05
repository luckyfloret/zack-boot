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
    /**
     * 获取通知公共分页列表
     * @param reqVO 请求参数
     * @return 通知公告分页列表
     */
    PageResult<Notice> getPage(NoticePageReqVO reqVO);

    /**
     * 创建通知公告
     * @param reqVO 请求参数
     */
    void createNotice(NoticeCreateReqVO reqVO);

    /**
     * 更新通知公告
     * @param reqVO 请求参数
     */
    void updateNotice(NoticeUpdateReqVO reqVO);

    /**
     * 根据id删除通知公告
     * @param id 通知公告id
     */
    void deleteNoticeById(Long id);

    /**
     * 更新通知公告状态
     * @param reqVO 请求参数
     */
    void updateStatus(NoticeUpdateStatusReqVO reqVO);

    /**
     *
     * @param id 通知公告id
     * @return Notice
     */
    Notice getNoticeById(Long id);
}
