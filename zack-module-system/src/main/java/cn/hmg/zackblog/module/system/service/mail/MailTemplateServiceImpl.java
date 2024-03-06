package cn.hmg.zackblog.module.system.service.mail;

import cn.hmg.zackblog.framework.common.exception.BusinessException;
import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.mail.core.factory.MailClientFactory;
import cn.hmg.zackblog.module.system.controller.admin.mail.vo.template.MailTemplateCreateReqVO;
import cn.hmg.zackblog.module.system.controller.admin.mail.vo.template.MailTemplatePageReqVO;
import cn.hmg.zackblog.module.system.controller.admin.mail.vo.template.MailTemplateUpdateReqVO;
import cn.hmg.zackblog.module.system.convert.mail.MailAccountConvert;
import cn.hmg.zackblog.module.system.convert.mail.MailTemplateConvert;
import cn.hmg.zackblog.module.system.entity.mail.MailAccount;
import cn.hmg.zackblog.module.system.entity.mail.MailTemplate;
import cn.hmg.zackblog.module.system.mapper.mail.MailTemplateMapper;
import cn.hmg.zackblog.module.system.mq.producer.mail.MailTemplateProducer;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import static cn.hmg.zackblog.framework.common.enums.CommonStatusEnum.verifyStatusIsExists;
import static cn.hmg.zackblog.framework.common.utils.collections.CollectionUtils.convertMap;
import static cn.hmg.zackblog.module.system.enums.ErrorCodeEnum.*;

/**
 * <p>
 * 邮件模板 服务实现类
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Service
@Slf4j
public class MailTemplateServiceImpl extends ServiceImpl<MailTemplateMapper, MailTemplate> implements IMailTemplateService {

    /**
     * 正则表达式，匹配 {} 中的变量
     */
    private static final Pattern PATTERN_PARAMS = Pattern.compile("\\{(.*?)}");

    @Getter
    private volatile Map<String, MailTemplate> mailTemplateCache;

    @Resource
    private MailTemplateMapper mailTemplateMapper;

    @Resource
    private IMailAccountService mailAccountService;

    @Resource
    private MailClientFactory mailClientFactory;

    @Resource
    private MailTemplateProducer mailTemplateProducer;

    @PostConstruct
    @Override
    public void initLocalCache() {
        List<MailTemplate> mailTemplateList = mailTemplateMapper.selectList();
        log.info("[initLocalCache] => 初始化邮箱模板缓存，数量为：{}", mailTemplateList.size());
        mailTemplateCache = convertMap(mailTemplateList, MailTemplate::getCode);
        //清空缓存，避免刷新缓存时脏数据的发生
        mailClientFactory.clear();
        mailTemplateList.forEach(mailTemplate -> {
            MailAccount mailAccount = mailAccountService.getMailAccountByIdFromCache(mailTemplate.getAccountId());
            mailClientFactory.createMailClient(mailTemplate.getCode(), MailAccountConvert.INSTANCE.convert(mailAccount),
                    MailTemplateConvert.INSTANCE.convert(mailTemplate));
        });
    }

    @Override
    public PageResult<MailTemplate> getPage(MailTemplatePageReqVO reqVO) {
        return mailTemplateMapper.getPage(reqVO);
    }

    @Override
    public MailTemplate getMailTemplateById(Long id) {
        return verifyMailTemplateIsExists(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createMailTemplate(MailTemplateCreateReqVO reqVO) {
        //校验模板名称是否唯一
        verifyTemplateNameIsUnique(null, reqVO.getName());

        //校验模板状态是否合法
        verifyMailTemplateStatus(reqVO.getStatus());

        //校验邮箱账号是否存在
        verifyMailAccountIsExists(reqVO.getAccountId());

        //解析参数
        MailTemplate mailTemplate = MailTemplateConvert.INSTANCE.convert(reqVO);
        mailTemplate.setParams(parseTemplateParams(reqVO.getContent()));

        //入库
        mailTemplateMapper.insert(mailTemplate);

        //刷新缓存
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                mailTemplateProducer.syncSendMailTemplateRefreshCacheMessage();
            }
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateMailTemplate(MailTemplateUpdateReqVO reqVO) {
        //校验邮箱模板是否存在
        verifyMailTemplateIsExists(reqVO.getId());

        //校验模板名称是否唯一
        verifyTemplateNameIsUnique(reqVO.getId(), reqVO.getName());

        //校验模板状态是否合法
        verifyMailTemplateStatus(reqVO.getStatus());

        //校验邮箱账号是否存在
        verifyMailAccountIsExists(reqVO.getAccountId());

        //解析参数
        MailTemplate mailTemplate = MailTemplateConvert.INSTANCE.convert(reqVO);
        mailTemplate.setParams(parseTemplateParams(reqVO.getContent()));

        //入库
        mailTemplateMapper.updateById(mailTemplate);

        //刷新缓存
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                mailTemplateProducer.syncSendMailTemplateRefreshCacheMessage();
            }
        });
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteMailTemplateById(Long id) {
        //校验邮件模板是否存在
        verifyMailTemplateIsExists(id);

        //删除
        mailTemplateMapper.deleteById(id);

        //刷新缓存
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                mailTemplateProducer.syncSendMailTemplateRefreshCacheMessage();
            }
        });
    }

    @Override
    public Long selectCountByAccountId(Long accountId) {
        return mailTemplateMapper.selectCountByAccountId(accountId);
    }

    @Override
    public MailTemplate getMailTemplateByCodeFromCache(String templateCode) {
        return mailTemplateCache.get(templateCode);
    }

    @Override
    public String formatTemplateParams(String content, Map<String, Object> params) {
        if (params.isEmpty()) {
            return content;
        }
        return StrUtil.format(content, params);
    }


    private void verifyMailTemplateStatus(Integer status) {
        if (!verifyStatusIsExists(status)) {
            throw new BusinessException(MAIL_TEMPLATE_STATUS_ERROR.getCode(), MAIL_TEMPLATE_STATUS_ERROR.getMessage());
        }
    }

    private MailTemplate verifyMailTemplateIsExists(Long id) {
        MailTemplate mailTemplate = mailTemplateMapper.selectById(id);
        if (Objects.isNull(mailTemplate)) {
            throw new BusinessException(MAIL_TEMPLATE_NOT_EXISTS.getCode(), MAIL_TEMPLATE_NOT_EXISTS.getMessage());
        }
        return mailTemplate;
    }

    private void verifyMailAccountIsExists(Long accountId) {
        mailAccountService.getMailAccountById(accountId);
    }

    private void verifyTemplateNameIsUnique(Long mailTemplateId, String name) {
        MailTemplate mailTemplate = mailTemplateMapper.selectByName(name);
        if (Objects.isNull(mailTemplate)) {
            return;
        }

        if (ObjUtil.notEqual(mailTemplate.getId(), mailTemplateId)) {
            throw new BusinessException(MAIL_TEMPLATE_NAME_ALREADY_EXISTS.getCode(), MAIL_TEMPLATE_NAME_ALREADY_EXISTS.getMessage());
        }
    }

    /**
     * 匹配 {} 里的参数
     *
     * @param templateContent 模板内容
     * @return 解析后的参数
     */
    private List<String> parseTemplateParams(String templateContent) {
        return ReUtil.findAllGroup1(PATTERN_PARAMS, templateContent);
    }
}
