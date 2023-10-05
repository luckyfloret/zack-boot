package cn.hmg.zackblog.module.infra.service.file;

import cn.hmg.zackblog.framework.common.exception.BusinessException;
import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.oss.core.OssClient;
import cn.hmg.zackblog.module.infra.controller.admin.file.vo.file.FilePageReqVO;
import cn.hmg.zackblog.module.infra.entity.file.File;
import cn.hmg.zackblog.module.infra.mapper.file.FileMapper;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.Objects;

import static cn.hmg.zackblog.framework.common.enums.CommonImageTypeEnum.verifyImageType;
import static cn.hmg.zackblog.framework.common.utils.io.FileUtils.*;
import static cn.hmg.zackblog.module.infra.enums.ErrorCodeEnum.FILE_NOT_EXISTS;
import static cn.hmg.zackblog.module.infra.enums.ErrorCodeEnum.FILE_TYPE_IS_ONLY_PICTURE;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hmg
 * @since 2023-08-27
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {

    @Resource
    private FileMapper fileMapper;

    @Resource
    private IFileConfigService fileConfigService;

    @Override
    public PageResult<File> getPage(FilePageReqVO reqVO) {
        return fileMapper.getPage(reqVO);
    }

    @Override
    public void deleteFileById(Long id) throws Exception {
        //校验文件是否存在
        File file = verifyFileIsExists(id);

        OssClient masterOssClient = fileConfigService.getMasterOssClient();
        Assert.notNull(masterOssClient, "客户端不能为空， id => {}", file.getConfigId());

        //oss删除
        masterOssClient.delete(file.getName());

        //db删除
        fileMapper.deleteById(id);
    }

    @Override
    public String uploadFile(InputStream inputStream, String filename) throws Exception {
        //获取文件类型
        String type = getType(inputStream, filename);
        //校验文件类型
        if (!verifyImageType(type)) {
            throw new BusinessException(FILE_TYPE_IS_ONLY_PICTURE.getCode(), FILE_TYPE_IS_ONLY_PICTURE.getMessage());
        }

        //获取主的oss客户端
        OssClient masterOssClient = fileConfigService.getMasterOssClient();
        Assert.notNull(masterOssClient, "master客户端不能为空");

        //oss上传
        String ossFilename = convertOssFilename(filename);
        byte[] fileContent = convertByte(inputStream);
        String url = masterOssClient.upload(fileContent,ossFilename , type);

        //构建file
        File file = new File();
        file.setConfigId(masterOssClient.getConfigId());
        file.setType(type);
        file.setName(filename);
        file.setOssFilename(ossFilename);
        file.setUrl(url);
        file.setSize(fileContent.length);

        //新增
        fileMapper.insert(file);
        return url;
    }

    /**
     * 校验文件是否存在
     * @param id 文件id
     * @return file
     */
    private File verifyFileIsExists(Long id) {
        File file = fileMapper.selectById(id);
        if (Objects.isNull(file)) {
            throw new BusinessException(FILE_NOT_EXISTS.getCode(), FILE_NOT_EXISTS.getMessage());
        }
        return file;
    }
}
