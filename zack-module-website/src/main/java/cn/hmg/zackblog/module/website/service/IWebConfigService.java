package cn.hmg.zackblog.module.website.service;

import cn.hmg.zackblog.module.website.controller.admin.vo.webconfig.AuthorInfoVO;
import cn.hmg.zackblog.module.website.controller.admin.vo.webconfig.WebsiteInfoVO;
import cn.hmg.zackblog.module.website.entity.WebConfig;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

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

    String upload(InputStream inputStream, String filename) throws Exception;
}
