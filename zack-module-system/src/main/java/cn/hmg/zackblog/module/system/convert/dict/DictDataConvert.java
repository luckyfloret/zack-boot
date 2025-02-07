package cn.hmg.zackblog.module.system.convert.dict;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.system.controller.admin.dict.vo.data.*;
import cn.hmg.zackblog.module.system.entity.dict.DictData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-01 17:25
 * @description: 字典数据转换
 */
@Mapper
public interface DictDataConvert {
    DictDataConvert INSTANCE = Mappers.getMapper(DictDataConvert.class);

    PageResult<DictDataPageRespVO> convertPage(PageResult<DictData> dictDataPageResult);

    DictData convert(DictDataCreateReqVO dictDataCreateReqVO);

    DictData convert(DictDataUpdateReqVO dictDataUpdateReqVO);

    DictDataRespVO convert(DictData dictData);

    List<DictDataListRespVO> convert(List<DictData> dictDataList);
}
