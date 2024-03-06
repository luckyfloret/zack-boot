package cn.hmg.zackblog.module.system.mapper.dict;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.mybatisplus.core.mapper.BaseMapperExtend;
import cn.hmg.zackblog.framework.mybatisplus.core.query.LambdaQueryWrapperExtend;
import cn.hmg.zackblog.module.system.controller.admin.dict.vo.type.DictTypePageReqVO;
import cn.hmg.zackblog.module.system.entity.dict.DictType;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 字典类型 Mapper 接口
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Mapper
public interface DictTypeMapper extends BaseMapperExtend<DictType> {

    default PageResult<DictType> getPage(DictTypePageReqVO dictTypePageReqVO) {
        return page(dictTypePageReqVO, new LambdaQueryWrapperExtend<DictType>()
                .likeIfExists(DictType::getName, dictTypePageReqVO.getName())
                .likeIfExists(DictType::getType, dictTypePageReqVO.getType())
                .eqIfExists(DictType::getStatus, dictTypePageReqVO.getStatus())
        );
    }

    default DictType selectByDictName(String name) {
        return selectOne(DictType::getName, name);
    }

    default DictType selectByDictType(String type) {
        return selectOne(DictType::getType, type);
    }
}
