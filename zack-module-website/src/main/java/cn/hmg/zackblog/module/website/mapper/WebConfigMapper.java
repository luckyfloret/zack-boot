package cn.hmg.zackblog.module.website.mapper;

import cn.hmg.zackblog.framework.mybatisplus.core.mapper.BaseMapperExtend;
import cn.hmg.zackblog.module.website.entity.WebConfig;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 网站配置 Mapper 接口
 * </p>
 *
 * @author hmg
 * @since 2023-08-30
 */
@Mapper
public interface WebConfigMapper extends BaseMapperExtend<WebConfig> {
    default Long selectCount() {
        return selectCount(new QueryWrapper<>());
    }
}
