package cn.hmg.zackblog.module.system.convert.dict;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.system.controller.admin.dict.vo.type.*;
import cn.hmg.zackblog.module.system.entity.dict.DictType;
import cn.hutool.core.lang.Dict;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-31 22:45
 * @description: 字典类型转换
 */
@Mapper
public interface DictTypeConvert {
    DictTypeConvert INSTANCE = Mappers.getMapper(DictTypeConvert.class);

    PageResult<DictTypePageRespVO> convert(PageResult<DictType> dictTypeList);

    DictType convert(DictTypeCreateReqVO dictTypeCreateReqVO);

    DictType convert(DictTypeUpdateReqVO dictTypeUpdateReqVO);

    DictTypeRespVO convert(DictType dictType);

    List<DictTypeListRespVO> convert(List<DictType> dictTypeList);
}
