package cn.hmg.zackblog.module.infra.service.file;

import cn.hmg.zackblog.framework.common.exception.BusinessException;
import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.oss.core.AbstractOssClient;
import cn.hmg.zackblog.framework.oss.core.client.s3.OssConfig;
import cn.hmg.zackblog.framework.oss.core.factory.OssClientFactory;
import cn.hmg.zackblog.framework.oss.core.OssClient;
import cn.hmg.zackblog.module.infra.controller.admin.file.vo.config.FileConfigCreateReqVO;
import cn.hmg.zackblog.module.infra.controller.admin.file.vo.config.FileConfigPageReqVO;
import cn.hmg.zackblog.module.infra.controller.admin.file.vo.config.FileConfigUpdateReqVO;
import cn.hmg.zackblog.module.infra.convert.file.config.FileConfigConvert;
import cn.hmg.zackblog.module.infra.entity.file.FileConfig;
import cn.hmg.zackblog.module.infra.mapper.file.FileConfigMapper;
import cn.hmg.zackblog.module.infra.mq.producer.file.config.FileConfigProducer;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static cn.hmg.zackblog.module.infra.enums.ErrorCodeEnum.FILE_CONFIG_IS_MASTER;
import static cn.hmg.zackblog.module.infra.enums.ErrorCodeEnum.FILE_CONFIG_NOT_EXISTS;

/**
 * <p>
 * 文件配置（例如本地、阿里OSS、七牛云上传等） 服务实现类
 * </p>
 *
 * @author hmg
 * @since 2023-08-27
 */
@Service
@Slf4j
public class FileConfigServiceImpl extends ServiceImpl<FileConfigMapper, FileConfig> implements IFileConfigService {

    @Getter
    private OssClient masterOssClient;

    @Resource
    private OssClientFactory ossClientFactory;

    @Resource
    private FileConfigMapper fileConfigMapper;

    @Resource
    private FileConfigProducer fileConfigProducer;

    @PostConstruct
    @Override
    public void initLocalCache() {
        log.info("initLocalCache 正在初始化...");
        List<FileConfig> fileConfigList = fileConfigMapper.selectList();
        //初始化前先清空缓存，保证下次刷新缓存时不出现脏数据
        ossClientFactory.clearCache();
        fileConfigList.forEach(fileConfig -> {
            OssConfig ossConfig = new OssConfig();
            BeanUtils.copyProperties(fileConfig, ossConfig);
            //初始化oss客户端
            ossClientFactory.createOssClient(fileConfig.getId(), ossConfig);

            //选出主配置oss客户端
            if (fileConfig.getMaster()) {
                masterOssClient = ossClientFactory.getOssClient(fileConfig.getId());
            }
        });
    }

    @Override
    public PageResult<FileConfig> getPage(FileConfigPageReqVO fileConfigPageReqVO) {
        return fileConfigMapper.getPage(fileConfigPageReqVO);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createFileConfig(FileConfigCreateReqVO reqVO) {
        fileConfigMapper.insert(FileConfigConvert.INSTANCE.convert(reqVO).setMaster(false));

        //刷新缓存
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                //MQ刷新缓存
                fileConfigProducer.syncSendFileConfigRefreshCacheMessage();
            }
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateFileConfigMaster(Long id) {
        //校验文件配置是否存在
        verifyFileConfigExists(id);
        //把所有文件配置都更新为不是主配置
        fileConfigMapper.updateBatch(new FileConfig().setMaster(false));
        //更新主配置
        fileConfigMapper.updateById(new FileConfig().setId(id).setMaster(true));
        //刷新缓存
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                //MQ刷新缓存
                fileConfigProducer.syncSendFileConfigRefreshCacheMessage();
            }
        });
    }

    @Override
    public String testUpload(Long id) throws Exception {
        verifyFileConfigExists(id);
        AbstractOssClient<OssConfig> ossClient = ossClientFactory.getOssClient(id);
        byte[] fileContent = ResourceUtil.readBytes("file/avatar.jpg");
        return ossClient.upload(fileContent, IdUtil.fastSimpleUUID() + ".jpg", "image/jpeg");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateFileConfig(FileConfigUpdateReqVO reqVO) {
        verifyFileConfigExists(reqVO.getId());
        fileConfigMapper.updateById(FileConfigConvert.INSTANCE.convert(reqVO));
        //刷新缓存
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                //MQ刷新缓存
                fileConfigProducer.syncSendFileConfigRefreshCacheMessage();
            }
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteFileConfigById(Long id) {
        FileConfig fileConfig = verifyFileConfigExists(id);
        if (fileConfig.getMaster()) {
            throw new BusinessException(FILE_CONFIG_IS_MASTER.getCode(), FILE_CONFIG_IS_MASTER.getMessage());
        }
        fileConfigMapper.deleteById(id);

        //刷新缓存
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                //MQ刷新缓存
                fileConfigProducer.syncSendFileConfigRefreshCacheMessage();
            }
        });
    }

    @Override
    public FileConfig getFileConfigById(Long id) {
        FileConfig fileConfig = verifyFileConfigExists(id);
        Assert.notNull(fileConfig, () -> new BusinessException(FILE_CONFIG_NOT_EXISTS.getCode(),
                FILE_CONFIG_NOT_EXISTS.getMessage()));
        return fileConfig;
    }

    private FileConfig verifyFileConfigExists(Long id) {
        FileConfig fileConfig = fileConfigMapper.selectById(id);
        if (Objects.isNull(fileConfig)) {
            throw new BusinessException(FILE_CONFIG_NOT_EXISTS.getCode(), FILE_CONFIG_NOT_EXISTS.getMessage());
        }
        return fileConfig;
    }
}
