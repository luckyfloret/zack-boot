package cn.hmg.zackblog.module.infra.service;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.oss.core.OssClient;
import cn.hmg.zackblog.module.infra.controller.admin.vo.config.FileConfigCreateReqVO;
import cn.hmg.zackblog.module.infra.controller.admin.vo.config.FileConfigPageReqVO;
import cn.hmg.zackblog.module.infra.controller.admin.vo.config.FileConfigUpdateReqVO;
import cn.hmg.zackblog.module.infra.entity.FileConfig;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 文件配置（例如本地、阿里OSS、七牛云上传等） 服务类
 * </p>
 *
 * @author hmg
 * @since 2023-08-27
 */
public interface IFileConfigService extends IService<FileConfig> {

    void initLocalCache();

    PageResult<FileConfig> getPage(FileConfigPageReqVO fileConfigPageReqVO);

    void createFileConfig(FileConfigCreateReqVO reqVO);

    void updateFileConfigMaster(Long id);

    String testUpload(Long id) throws Exception;

    void updateFileConfig(FileConfigUpdateReqVO reqVO);

    void deleteFileConfigById(Long id);

    FileConfig getFileConfigById(Long id);

    OssClient getMasterOssClient();
}
