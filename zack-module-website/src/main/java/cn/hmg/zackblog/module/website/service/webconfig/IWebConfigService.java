package cn.hmg.zackblog.module.website.service.webconfig;

import cn.hmg.zackblog.module.website.controller.admin.webconfig.vo.AuthorInfoVO;
import cn.hmg.zackblog.module.website.controller.admin.webconfig.vo.WebsiteInfoVO;
import cn.hmg.zackblog.module.website.entity.webconfig.WebConfig;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 网站配置 服务类
 * </p>
 *
 * @author hmg
 * @since 2023-08-30
 */
public interface IWebConfigService extends IService<WebConfig> {

    WebConfig getWebSiteInfo();

    WebConfig getAuthorInfo();

    void updateWebsiteInfo(WebsiteInfoVO websiteInfoVO);

    void updateAuthorInfo(AuthorInfoVO authorInfoVO);
}
