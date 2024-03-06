package cn.hmg.zackblog.module.system.mapper.mail;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.mybatisplus.core.mapper.BaseMapperExtend;
import cn.hmg.zackblog.framework.mybatisplus.core.query.LambdaQueryWrapperExtend;
import cn.hmg.zackblog.module.system.controller.admin.mail.vo.account.MailAccountPageReqVO;
import cn.hmg.zackblog.module.system.entity.mail.MailAccount;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 邮件账号管理 Mapper 接口
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Mapper
public interface MailAccountMapper extends BaseMapperExtend<MailAccount> {

    default PageResult<MailAccount> getPage(MailAccountPageReqVO reqVO) {
        return page(reqVO, new LambdaQueryWrapperExtend<MailAccount>()
                .likeIfExists(MailAccount::getUsername, reqVO.getUsername())
                .likeIfExists(MailAccount::getEmail, reqVO.getUsername())
        );
    }

    default MailAccount selectByEmail(String email) {
        return selectOne(new LambdaQueryWrapperExtend<MailAccount>()
                .eq(MailAccount::getEmail, email)
        );
    }

    default void updateBatch(MailAccount mailAccount) {
        update(mailAccount, new UpdateWrapper<>());
    }
}
