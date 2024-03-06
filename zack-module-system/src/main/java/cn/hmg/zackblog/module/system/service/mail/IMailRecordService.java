package cn.hmg.zackblog.module.system.service.mail;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.system.controller.admin.mail.vo.record.MailRecordPageReqVO;
import cn.hmg.zackblog.module.system.entity.mail.MailAccount;
import cn.hmg.zackblog.module.system.entity.mail.MailRecord;
import cn.hmg.zackblog.module.system.entity.mail.MailTemplate;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * <p>
 * 邮件记录 服务类
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
public interface IMailRecordService extends IService<MailRecord> {
    void createMailRecord(Long userId, Integer userType, String toMail, MailAccount mailAccount,
                          MailTemplate mailTemplate, String templateContent, Map<String, Object> templateParams,
                          Integer sendStatus, LocalDateTime sendTime, Exception exception);

    PageResult<MailRecord> getPage(MailRecordPageReqVO reqVO);

    MailRecord getMailRecordById(Long id);
}
