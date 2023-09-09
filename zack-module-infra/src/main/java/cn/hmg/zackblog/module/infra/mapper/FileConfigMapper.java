package cn.hmg.zackblog.module.infra.mapper;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.mybatisplus.core.mapper.BaseMapperExtend;
import cn.hmg.zackblog.framework.mybatisplus.core.query.LambdaQueryWrapperExtend;
import cn.hmg.zackblog.module.infra.controller.admin.vo.config.FileConfigPageReqVO;
import cn.hmg.zackblog.module.infra.entity.FileConfig;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 文件配置（例如本地、阿里OSS、七牛云上传等） Mapper 接口
 * </p>
 *
 * @author hmg
 * @since 2023-08-27
 */
@Mapper
public interface FileConfigMapper extends BaseMapperExtend<FileConfig> {

    default PageResult<FileConfig> getPage(FileConfigPageReqVO fileConfigPageReqVO){
        return page(fileConfigPageReqVO, new LambdaQueryWrapperExtend<FileConfig>()
                .likeIfExists(FileConfig::getName, fileConfigPageReqVO.getName())
        );
    }

    default void updateBatch(FileConfig fileConfig){
        update(fileConfig, new UpdateWrapper<>());
    }
}
