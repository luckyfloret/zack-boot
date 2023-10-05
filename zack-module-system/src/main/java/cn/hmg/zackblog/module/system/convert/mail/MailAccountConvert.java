package cn.hmg.zackblog.module.system.convert.mail;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.mail.core.client.SMTPConfig;
import cn.hmg.zackblog.module.system.controller.admin.mail.vo.account.*;
import cn.hmg.zackblog.module.system.entity.mail.MailAccount;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-20 0:01
 * @description:
 */
@Mapper
public interface MailAccountConvert {
    MailAccountConvert INSTANCE = Mappers.getMapper(MailAccountConvert.class);

    SMTPConfig convert(MailAccount mailAccount);

    PageResult<MailAccountPageRespVO> convert(PageResult<MailAccount> pageResult);

    MailAccountRespVO convertMailAccountRespVO(MailAccount mailAccount);

    MailAccount convert(MailAccountCreateReqVO reqVO);

    MailAccount convert(MailAccountUpdateReqVO reqVO);

    List<MailAccountListSimpleRespVO> convert(List<MailAccount> mailAccountList);
}
