package cn.hmg.zackblog.module.website.convert.admin.webconfig;

import cn.hmg.zackblog.module.website.controller.admin.webconfig.vo.AuthorInfoVO;
import cn.hmg.zackblog.module.website.controller.admin.webconfig.vo.WebsiteInfoVO;
import cn.hmg.zackblog.module.website.entity.webconfig.WebConfig;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-04 14:39
 * @description:
 */
@Mapper
public interface WebConfigConvert {
    WebConfigConvert INSTANCE = Mappers.getMapper(WebConfigConvert.class);

    WebsiteInfoVO convert(WebConfig webConfig);

    AuthorInfoVO convertToAuthorInfoVO(WebConfig webConfig);

    WebConfig convert(WebsiteInfoVO websiteInfoVO);

    WebConfig convert(AuthorInfoVO authorInfoVO);
}
