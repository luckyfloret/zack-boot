package cn.hmg.zackblog.module.infra.convert.file;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.infra.controller.admin.file.vo.file.FilePageRespVO;
import cn.hmg.zackblog.module.infra.entity.file.File;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-06 17:00
 * @description:
 */
@Mapper
public interface FileConvert {
    FileConvert INSTANCE = Mappers.getMapper(FileConvert.class);

    PageResult<FilePageRespVO> convert(PageResult<File> pageResult);
}
