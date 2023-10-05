package cn.hmg.zackblog.module.system.convert.mail;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.mail.core.client.SMTPMessageTemplate;
import cn.hmg.zackblog.module.system.controller.admin.mail.vo.template.MailTemplateCreateReqVO;
import cn.hmg.zackblog.module.system.controller.admin.mail.vo.template.MailTemplatePageRespVO;
import cn.hmg.zackblog.module.system.controller.admin.mail.vo.template.MailTemplateRespVO;
import cn.hmg.zackblog.module.system.controller.admin.mail.vo.template.MailTemplateUpdateReqVO;
import cn.hmg.zackblog.module.system.entity.mail.MailTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-20 0:02
 * @description:
 */
@Mapper
public interface MailTemplateConvert {
    MailTemplateConvert INSTANCE = Mappers.getMapper(MailTemplateConvert.class);

    SMTPMessageTemplate convert(MailTemplate mailTemplate);

    PageResult<MailTemplatePageRespVO> convert(PageResult<MailTemplate> pageResult);

    MailTemplateRespVO convertMailTemplateRespVO(MailTemplate mailTemplate);

    MailTemplate convert(MailTemplateCreateReqVO reqVO);

    MailTemplate convert(MailTemplateUpdateReqVO reqVO);
}
