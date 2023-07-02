package cn.hmg.zackblog.module.system.service.mail;

import cn.hmg.zackblog.module.system.entity.mail.MailTemplate;
import cn.hmg.zackblog.module.system.mapper.mail.MailTemplateMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 邮件模板 服务实现类
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Service
public class MailTemplateServiceImpl extends ServiceImpl<MailTemplateMapper, MailTemplate> implements IMailTemplateService {

}
