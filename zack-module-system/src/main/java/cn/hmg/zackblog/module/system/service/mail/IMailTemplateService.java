package cn.hmg.zackblog.module.system.service.mail;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.mail.core.MailClient;
import cn.hmg.zackblog.module.system.controller.admin.mail.vo.template.MailTemplateCreateReqVO;
import cn.hmg.zackblog.module.system.controller.admin.mail.vo.template.MailTemplatePageReqVO;
import cn.hmg.zackblog.module.system.controller.admin.mail.vo.template.MailTemplateUpdateReqVO;
import cn.hmg.zackblog.module.system.entity.mail.MailTemplate;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 邮件模板 服务类
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
public interface IMailTemplateService extends IService<MailTemplate> {
    void initLocalCache();

    PageResult<MailTemplate> getPage(MailTemplatePageReqVO reqVO);

    MailTemplate getMailTemplateById(Long id);

    void createMailTemplate(MailTemplateCreateReqVO reqVO);

    void updateMailTemplate(MailTemplateUpdateReqVO reqVO);

    void deleteMailTemplateById(Long id);

    Long selectCountByAccountId(Long accountId);

    MailTemplate getMailTemplateByCodeFromCache(String templateCode);

    String formatTemplateParams(String content, Map<String, Object> params);
}
