package cn.hmg.zackblog.module.system.service.mail;

import cn.hmg.zackblog.module.system.entity.mail.MailRecord;
import cn.hmg.zackblog.module.system.mapper.mail.MailRecordMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 邮件记录 服务实现类
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Service
public class MailRecordServiceImpl extends ServiceImpl<MailRecordMapper, MailRecord> implements IMailRecordService {

}
