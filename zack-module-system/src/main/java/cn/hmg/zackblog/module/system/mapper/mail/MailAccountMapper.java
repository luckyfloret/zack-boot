package cn.hmg.zackblog.module.system.mapper.mail;

import cn.hmg.zackblog.framework.mybatisplus.core.mapper.BaseMapperExtend;
import cn.hmg.zackblog.module.system.entity.mail.MailAccount;
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

}
