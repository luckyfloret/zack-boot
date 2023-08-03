package cn.hmg.zackblog.module.system.mapper.dict;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.mybatisplus.core.mapper.BaseMapperExtend;
import cn.hmg.zackblog.framework.mybatisplus.core.query.LambdaQueryWrapperExtend;
import cn.hmg.zackblog.module.system.controller.admin.dict.vo.data.DictDataPageReqVO;
import cn.hmg.zackblog.module.system.entity.dict.DictData;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 数据字典 Mapper 接口
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Mapper
public interface DictDataMapper extends BaseMapperExtend<DictData> {

    default Long selectCountByDictType(String type){
        return selectCount(DictData::getDictType, type);
    }

    default PageResult<DictData> getPage(DictDataPageReqVO dictDataPageReqVO) {
        return page(dictDataPageReqVO, new LambdaQueryWrapperExtend<DictData>()
                .eqIfExists(DictData::getDictType, dictDataPageReqVO.getDictType())
                .eqIfExists(DictData::getStatus, dictDataPageReqVO.getStatus())
                .orderByAsc(DictData::getSort)
        );
    }

    default DictData selectOne(String dictType, String value) {
        return selectOne(DictData::getDictType, dictType, DictData::getValue, value);
    }
}
