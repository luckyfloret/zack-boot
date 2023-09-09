package cn.hmg.zackblog.module.website.service.impl;

import cn.hmg.zackblog.framework.common.enums.CommonStatusEnum;
import cn.hmg.zackblog.framework.common.exception.BusinessException;
import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.website.controller.admin.vo.notice.NoticeCreateReqVO;
import cn.hmg.zackblog.module.website.controller.admin.vo.notice.NoticePageReqVO;
import cn.hmg.zackblog.module.website.controller.admin.vo.notice.NoticeUpdateReqVO;
import cn.hmg.zackblog.module.website.controller.admin.vo.notice.NoticeUpdateStatusReqVO;
import cn.hmg.zackblog.module.website.convert.admin.notice.NoticeConvert;
import cn.hmg.zackblog.module.website.entity.Notice;
import cn.hmg.zackblog.module.website.mapper.NoticeMapper;
import cn.hmg.zackblog.module.website.service.INoticeService;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static cn.hmg.zackblog.framework.common.enums.CommonStatusEnum.*;
import static cn.hmg.zackblog.module.website.enums.ErrorCodeEnum.*;
import static cn.hmg.zackblog.module.website.enums.NoticeTypeEnum.verifyNoticeTypeIsExists;

/**
 * <p>
 * 消息管理 服务实现类
 * </p>
 *
 * @author hmg
 * @since 2023-08-30
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

    @Resource
    private NoticeMapper noticeMapper;

    @Override
    public PageResult<Notice> getPage(NoticePageReqVO reqVO) {
        return noticeMapper.getPage(reqVO);
    }

    @Override
    public void createNotice(NoticeCreateReqVO reqVO) {
        noticeMapper.insert(NoticeConvert.INSTANCE.convert(reqVO).setStatus(DISABLED.getStatusCode()));
    }

    @Override
    public void updateNotice(NoticeUpdateReqVO reqVO) {
        //校验是否存在
        verifyNoticeIsExists(reqVO.getId());

        noticeMapper.updateById(NoticeConvert.INSTANCE.convert(reqVO));
    }

    @Override
    public void deleteNoticeById(Long id) {
        verifyNoticeIsExists(id);
        noticeMapper.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateStatus(NoticeUpdateStatusReqVO reqVO) {
        //校验是否存在
        Notice notice = verifyNoticeIsExists(reqVO.getId());
        //校验状态
        verifyNoticeStatus(notice.getStatus(), reqVO.getStatus());
        //校验类型
        verifyNoticeType(notice.getType(), reqVO.getType());

        List<Notice> noticeList = noticeMapper.selectListByType(reqVO.getType());
        noticeList = noticeList.stream().map(n -> n.setStatus(DISABLED.getStatusCode())).collect(Collectors.toList());
        noticeMapper.updateBatch(noticeList);
        noticeMapper.updateById(NoticeConvert.INSTANCE.convert(reqVO));
    }

    @Override
    public Notice getNoticeById(Long id) {
        return verifyNoticeIsExists(id);
    }

    private void verifyNoticeType(Integer typeFromDb, Integer typeFromReq) {
        //校验类型是否合法
        Assert.isTrue(verifyNoticeTypeIsExists(typeFromReq),
                () -> new BusinessException(NOTICE_TYPE_ERROR.getCode(), NOTICE_TYPE_ERROR.getMessage()));

        //校验类型是否属于非法传递
        Assert.isTrue(typeFromDb.equals(typeFromReq),
                () -> new BusinessException(NOTICE_TYPE_INCONSISTENT_WITH_DB.getCode(),
                        NOTICE_TYPE_INCONSISTENT_WITH_DB.getMessage()));
    }

    private void verifyNoticeStatus(Integer statusFromDb, Integer statusFromReq) {
        if (!verifyStatusIsExists(statusFromReq)) {
            throw new BusinessException(NOTICE_STATUS_ERROR.getCode(), NOTICE_STATUS_ERROR.getMessage());
        }

        if (statusFromDb.equals(statusFromReq) && ENABLED.getStatusCode().equals(statusFromReq)) {
            throw new BusinessException(NOTICE_ALREADY_ENABLE.getCode(), NOTICE_ALREADY_ENABLE.getMessage());
        }
    }

    private Notice verifyNoticeIsExists(Long id) {
        Notice notice = noticeMapper.selectById(id);
        if (Objects.isNull(notice)) {
            throw new BusinessException(NOTICE_NOT_EXISTS.getCode(), NOTICE_NOT_EXISTS.getMessage());
        }
        return notice;
    }
}
