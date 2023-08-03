package cn.hmg.zackblog.module.system.convert.dict;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.system.controller.admin.dict.vo.type.DictTypeCreateReqVO;
import cn.hmg.zackblog.module.system.controller.admin.dict.vo.type.DictTypePageRespVO;
import cn.hmg.zackblog.module.system.controller.admin.dict.vo.type.DictTypeRespVO;
import cn.hmg.zackblog.module.system.controller.admin.dict.vo.type.DictTypeUpdateReqVO;
import cn.hmg.zackblog.module.system.entity.dict.DictType;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

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
}
