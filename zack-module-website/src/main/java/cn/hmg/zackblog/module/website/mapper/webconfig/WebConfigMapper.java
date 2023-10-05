package cn.hmg.zackblog.module.website.mapper.webconfig;

import cn.hmg.zackblog.framework.mybatisplus.core.mapper.BaseMapperExtend;
import cn.hmg.zackblog.module.website.entity.webconfig.WebConfig;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
