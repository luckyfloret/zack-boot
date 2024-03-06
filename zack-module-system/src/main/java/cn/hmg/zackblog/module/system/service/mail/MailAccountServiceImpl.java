package cn.hmg.zackblog.module.system.service.mail;

import cn.hmg.zackblog.framework.common.exception.BusinessException;
import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.system.controller.admin.mail.vo.account.MailAccountCreateReqVO;
import cn.hmg.zackblog.module.system.controller.admin.mail.vo.account.MailAccountPageReqVO;
import cn.hmg.zackblog.module.system.controller.admin.mail.vo.account.MailAccountUpdateReqVO;
import cn.hmg.zackblog.module.system.convert.mail.MailAccountConvert;
import cn.hmg.zackblog.module.system.entity.mail.MailAccount;
import cn.hmg.zackblog.module.system.mapper.mail.MailAccountMapper;
import cn.hmg.zackblog.module.system.mq.producer.mail.MailAccountProducer;
import cn.hmg.zackblog.module.system.mq.producer.mail.MailTemplateProducer;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

import static cn.hmg.zackblog.framework.common.utils.collections.CollectionUtils.convertMap;
import static cn.hmg.zackblog.module.system.enums.ErrorCodeEnum.*;

/**
 * <p>
 * 邮件账号管理 服务实现类
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Service
@Slf4j
public class MailAccountServiceImpl extends ServiceImpl<MailAccountMapper, MailAccount> implements IMailAccountService {

    @Getter
    private volatile Map<Long, MailAccount> mailAccountCache;

    @Resource
    @Lazy
    private IMailTemplateService mailTemplateService;

    @Resource
    private MailAccountMapper mailAccountMapper;

    @Resource
    private MailAccountProducer mailAccountProducer;

    @Resource
    private MailTemplateProducer mailTemplateProducer;

    @PostConstruct
    @Override
    public void initLocalCache() {
        List<MailAccount> mailAccountList = mailAccountMapper.selectList();
        log.info("[initLocalCache] => 初始化邮箱账户缓存，数量为：{}", mailAccountList.size());
        mailAccountCache = convertMap(mailAccountList, MailAccount::getId);
    }

    @Override
    public PageResult<MailAccount> getPage(MailAccountPageReqVO reqVO) {
        return mailAccountMapper.getPage(reqVO);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createMailAccount(MailAccountCreateReqVO reqVO) {
        //校验邮箱是否唯一
        verifyMailIsUnique(null, reqVO.getEmail());

        //插入
        mailAccountMapper.insert(MailAccountConvert.INSTANCE.convert(reqVO));

        //刷新缓存
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                mailAccountProducer.syncSendMailAccountRefreshCacheMessage();
                mailTemplateProducer.syncSendMailTemplateRefreshCacheMessage();
            }
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateMailAccount(MailAccountUpdateReqVO reqVO) {
        //校验是否存在
        verifyMailAccountIsExists(reqVO.getId());

        //校验邮箱是否唯一
        verifyMailIsUnique(reqVO.getId(), reqVO.getEmail());

        //更新
        mailAccountMapper.updateById(MailAccountConvert.INSTANCE.convert(reqVO));

        //刷新缓存
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                mailAccountProducer.syncSendMailAccountRefreshCacheMessage();
                mailTemplateProducer.syncSendMailTemplateRefreshCacheMessage();
            }
        });
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteMailAccountById(Long id) {
        //校验是否存在
        verifyMailAccountIsExists(id);

        //校验是否和模板有关联
        if (mailTemplateService.selectCountByAccountId(id) > 0) {
            throw new BusinessException(MAIL_ACCOUNT_IS_ASSOCIATED_WITH_TEMPLATE.getCode(),
                    MAIL_ACCOUNT_IS_ASSOCIATED_WITH_TEMPLATE.getMessage());
        }

        //删除
        mailAccountMapper.deleteById(id);

        //刷新缓存
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                mailAccountProducer.syncSendMailAccountRefreshCacheMessage();
            }
        });
    }

    @Override
    public MailAccount getMailAccountById(Long id) {
        return verifyMailAccountIsExists(id);
    }

    @Override
    public MailAccount getMailAccountByIdFromCache(Long id) {
        return mailAccountCache.get(id);
    }

    @Override
    public MailAccount selectOneByIdFromCache(Long accountId) {
        return mailAccountCache.get(accountId);
    }

    private MailAccount verifyMailAccountIsExists(Long id) {
        MailAccount mailAccount = mailAccountMapper.selectById(id);
        if (Objects.isNull(mailAccount)) {
            throw new BusinessException(MAIL_ACCOUNT_NOT_EXISTS.getCode(), MAIL_ACCOUNT_NOT_EXISTS.getMessage());
        }
        return mailAccount;
    }

    private void verifyMailIsUnique(Long mailAccountId, String email) {
        MailAccount mailAccount = mailAccountMapper.selectByEmail(email);

        if (Objects.isNull(mailAccount)) {
            return;
        }

        if (ObjUtil.notEqual(mailAccount.getId(), mailAccountId)) {
            throw new BusinessException(MAIL_ACCOUNT_ALREADY_EXISTS.getCode(), MAIL_ACCOUNT_ALREADY_EXISTS.getMessage());
        }
    }
}
