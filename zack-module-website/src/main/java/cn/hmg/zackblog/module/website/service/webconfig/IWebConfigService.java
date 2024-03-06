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
    /**
     * 获取网站信息
     * @return WebConfig
     */
    WebConfig getWebSiteInfo();

    /**
     * 获取作者信息
     * @return WebConfig
     */
    WebConfig getAuthorInfo();

    /**
     * 更新网站信息
     * @param websiteInfoVO 网站信息vo
     */
    void updateWebsiteInfo(WebsiteInfoVO websiteInfoVO);

    /**
     * 更新作者信息
     * @param authorInfoVO 作者信息vo
     */
    void updateAuthorInfo(AuthorInfoVO authorInfoVO);
}
