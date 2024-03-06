package cn.hmg.zackblog.module.system.mapper.mail;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.mybatisplus.core.mapper.BaseMapperExtend;
import cn.hmg.zackblog.framework.mybatisplus.core.query.LambdaQueryWrapperExtend;
import cn.hmg.zackblog.module.system.controller.admin.mail.vo.record.MailRecordPageReqVO;
import cn.hmg.zackblog.module.system.entity.mail.MailRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 邮件记录 Mapper 接口
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Mapper
public interface MailRecordMapper extends BaseMapperExtend<MailRecord> {

    default PageResult<MailRecord> getPage(MailRecordPageReqVO reqVO) {
        return page(reqVO, new LambdaQueryWrapperExtend<MailRecord>()
                .eqIfExists(MailRecord::getAccountId, reqVO.getAccountId())
                .eqIfExists(MailRecord::getSendStatus, reqVO.getSendStatus())
                .eqIfExists(MailRecord::getUserType, reqVO.getUserType())
                .likeIfExists(MailRecord::getTemplateCode, reqVO.getTemplateCode())
        );
    }
}
