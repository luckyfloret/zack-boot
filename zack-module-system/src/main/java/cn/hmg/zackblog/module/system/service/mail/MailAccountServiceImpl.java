package cn.hmg.zackblog.module.system.service.mail;

import cn.hmg.zackblog.module.system.entity.mail.MailAccount;
import cn.hmg.zackblog.module.system.mapper.mail.MailAccountMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 邮件账号管理 服务实现类
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Service
public class MailAccountServiceImpl extends ServiceImpl<MailAccountMapper, MailAccount> implements IMailAccountService {

}
