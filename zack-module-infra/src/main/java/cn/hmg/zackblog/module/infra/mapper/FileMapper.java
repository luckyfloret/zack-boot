package cn.hmg.zackblog.module.infra.mapper;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.mybatisplus.core.mapper.BaseMapperExtend;
import cn.hmg.zackblog.framework.mybatisplus.core.query.LambdaQueryWrapperExtend;
import cn.hmg.zackblog.module.infra.controller.admin.vo.file.FilePageReqVO;
import cn.hmg.zackblog.module.infra.entity.File;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author hmg
 * @since 2023-08-27
 */
@Mapper
public interface FileMapper extends BaseMapperExtend<File> {

    default PageResult<File> getPage(FilePageReqVO reqVO) {
        return page(reqVO, new LambdaQueryWrapperExtend<File>()
                .likeIfExists(File::getName, reqVO.getFilename())
        );
    }
}
