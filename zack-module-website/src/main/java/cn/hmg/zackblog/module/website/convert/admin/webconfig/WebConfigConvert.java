package cn.hmg.zackblog.module.website.convert.admin.webconfig;

import cn.hmg.zackblog.framework.common.utils.collections.CollectionUtils;
import cn.hmg.zackblog.module.website.controller.admin.vo.webconfig.AuthorInfoVO;
import cn.hmg.zackblog.module.website.controller.admin.vo.webconfig.WebsiteInfoVO;
import cn.hmg.zackblog.module.website.entity.WebConfig;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
