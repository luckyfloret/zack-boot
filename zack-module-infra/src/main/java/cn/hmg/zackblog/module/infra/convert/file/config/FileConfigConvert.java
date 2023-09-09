package cn.hmg.zackblog.module.infra.convert.file.config;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.infra.controller.admin.vo.config.FileConfigCreateReqVO;
import cn.hmg.zackblog.module.infra.controller.admin.vo.config.FileConfigPageRespVO;
import cn.hmg.zackblog.module.infra.controller.admin.vo.config.FileConfigRespVO;
import cn.hmg.zackblog.module.infra.controller.admin.vo.config.FileConfigUpdateReqVO;
import cn.hmg.zackblog.module.infra.entity.FileConfig;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-28 14:26
 * @description:
 */
@Mapper
public interface FileConfigConvert {
    FileConfigConvert INSTANCE = Mappers.getMapper(FileConfigConvert.class);

    PageResult<FileConfigPageRespVO> convert(PageResult<FileConfig> pageResult);

    FileConfig convert(FileConfigCreateReqVO reqVO);

    FileConfig convert(FileConfigUpdateReqVO reqVO);

    FileConfigRespVO convert(FileConfig fileConfig);
}
