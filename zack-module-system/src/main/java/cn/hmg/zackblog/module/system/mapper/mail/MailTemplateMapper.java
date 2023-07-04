package cn.hmg.zackblog.module.system.mapper.mail;

import cn.hmg.zackblog.module.system.entity.mail.MailTemplate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface MailTemplateMapper extends BaseMapper<MailTemplate> {

}
