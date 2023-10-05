package cn.hmg.zackblog.module.system.convert.mail;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.system.controller.admin.mail.vo.record.MailRecordPageRespVO;
import cn.hmg.zackblog.module.system.controller.admin.mail.vo.record.MailRecordRespVO;
import cn.hmg.zackblog.module.system.entity.mail.MailRecord;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-10-04 0:18
 * @description:
 */
@Mapper
public interface MailRecordConvert {
    MailRecordConvert INSTANCE = Mappers.getMapper(MailRecordConvert.class);

    PageResult<MailRecordPageRespVO> convert(PageResult<MailRecord> pageResult);

    MailRecordRespVO convert(MailRecord mailRecord);
}
