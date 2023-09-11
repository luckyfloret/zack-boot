package cn.hmg.zackblog.module.website.service.impl;

import cn.hmg.zackblog.framework.common.exception.BusinessException;
import cn.hmg.zackblog.framework.common.utils.collections.CollectionUtils;
import cn.hmg.zackblog.module.infra.service.IFileService;
import cn.hmg.zackblog.module.website.controller.admin.vo.webconfig.AuthorInfoVO;
import cn.hmg.zackblog.module.website.controller.admin.vo.webconfig.WebsiteInfoVO;
import cn.hmg.zackblog.module.website.convert.admin.webconfig.WebConfigConvert;
import cn.hmg.zackblog.module.website.entity.WebConfig;
import cn.hmg.zackblog.module.website.mapper.WebConfigMapper;
import cn.hmg.zackblog.module.website.service.IWebConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import static cn.hmg.zackblog.module.website.enums.ErrorCodeEnum.WEB_CONFIG_NOT_EXISTS;

/**
 * <p>
 * 网站配置 服务实现类
 * </p>
 *
 * @author hmg
 * @since 2023-08-30
 */
@Service
public class WebConfigServiceImpl extends ServiceImpl<WebConfigMapper, WebConfig> implements IWebConfigService {

    @Resource
    private WebConfigMapper webConfigMapper;

    @Resource
    private IFileService fileService;

    @Override
    public WebConfig getWebSiteInfo() {
        List<WebConfig> webConfigs = webConfigMapper.selectList();
        return !CollectionUtils.isEmpty(webConfigs) ? webConfigs.get(0) : null;
    }

    @Override
    public WebConfig getAuthorInfo() {
        List<WebConfig> webConfigs = webConfigMapper.selectList();
        return !CollectionUtils.isEmpty(webConfigs) ? webConfigs.get(0) : null;
    }

    @Override
    public void updateWebsiteInfo(WebsiteInfoVO websiteInfoVO) {
        createOrUpdate(WebConfigConvert.INSTANCE.convert(websiteInfoVO));
    }

    @Override
    public void updateAuthorInfo(AuthorInfoVO authorInfoVO) {
        createOrUpdate(WebConfigConvert.INSTANCE.convert(authorInfoVO));
    }

    private void createOrUpdate(WebConfig webConfig) {
        if (webConfigMapper.selectCount() > 0) {
            verifyIsExists(webConfig.getId());
            webConfigMapper.updateById(webConfig);
        }else {
            webConfigMapper.insert(webConfig);
        }
    }

    private void verifyIsExists(Long id) {
        WebConfig webConfig = webConfigMapper.selectById(id);
        if (Objects.isNull(webConfig)) {
            throw new BusinessException(WEB_CONFIG_NOT_EXISTS.getCode(), WEB_CONFIG_NOT_EXISTS.getMessage());
        }
    }
}
