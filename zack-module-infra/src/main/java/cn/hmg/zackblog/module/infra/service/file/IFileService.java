package cn.hmg.zackblog.module.infra.service.file;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.infra.controller.admin.file.vo.file.FilePageReqVO;
import cn.hmg.zackblog.module.infra.entity.file.File;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.InputStream;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hmg
 * @since 2023-08-27
 */
public interface IFileService extends IService<File> {

    PageResult<File> getPage(FilePageReqVO reqVO);

    void deleteFileById(Long id) throws Exception;

    String uploadFile(InputStream inputStream, String filename) throws Exception;
}
