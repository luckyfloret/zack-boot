package cn.hmg.zackblog.module.system.mapper.mail;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.mybatisplus.core.mapper.BaseMapperExtend;
import cn.hmg.zackblog.framework.mybatisplus.core.query.LambdaQueryWrapperExtend;
import cn.hmg.zackblog.module.system.controller.admin.mail.vo.template.MailTemplatePageReqVO;
import cn.hmg.zackblog.module.system.entity.mail.MailTemplate;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 邮件模板 Mapper 接口
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Mapper
public interface MailTemplateMapper extends BaseMapperExtend<MailTemplate> {

    default PageResult<MailTemplate> getPage(MailTemplatePageReqVO reqVO) {
        return page(reqVO, new LambdaQueryWrapperExtend<MailTemplate>()
                .likeIfExists(MailTemplate::getName, reqVO.getName())
                .likeIfExists(MailTemplate::getCode, reqVO.getCode())
                .eqIfExists(MailTemplate::getAccountId, reqVO.getAccountId())
                .eqIfExists(MailTemplate::getStatus, reqVO.getStatus())
        );
    }

    default MailTemplate selectByName(String name) {
        return selectOne(new LambdaQueryWrapper<MailTemplate>().eq(MailTemplate::getName, name));
    }

    default Long selectCountByAccountId(Long accountId) {
        return selectCount(MailTemplate::getAccountId, accountId);
    }

    default MailTemplate selectByAccountId(Long accountId) {
        return selectOne(MailTemplate::getAccountId, accountId);
    }
}
