package cn.hmg.zackblog.module.system.service.mail;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.mail.core.MailClient;
import cn.hmg.zackblog.module.system.controller.admin.mail.vo.account.MailAccountCreateReqVO;
import cn.hmg.zackblog.module.system.controller.admin.mail.vo.account.MailAccountPageReqVO;
import cn.hmg.zackblog.module.system.controller.admin.mail.vo.account.MailAccountUpdateReqVO;
import cn.hmg.zackblog.module.system.entity.mail.MailAccount;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 邮件账号管理 服务类
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
public interface IMailAccountService extends IService<MailAccount> {
    void initLocalCache();

    PageResult<MailAccount> getPage(MailAccountPageReqVO reqVO);

    void createMailAccount(MailAccountCreateReqVO reqVO);

    void updateMailAccount(MailAccountUpdateReqVO reqVO);

    void deleteMailAccountById(Long id);

    MailAccount getMailAccountById(Long id);

    MailAccount getMailAccountByIdFromCache(Long id);

    MailAccount selectOneByIdFromCache(Long accountId);
}
