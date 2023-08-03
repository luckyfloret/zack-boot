package cn.hmg.zackblog.module.system.service.dict;

import cn.hmg.zackblog.framework.common.enums.CommonStatusEnum;
import cn.hmg.zackblog.framework.common.exception.BusinessException;
import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.system.controller.admin.dict.vo.type.DictTypeCreateReqVO;
import cn.hmg.zackblog.module.system.controller.admin.dict.vo.type.DictTypePageReqVO;
import cn.hmg.zackblog.module.system.controller.admin.dict.vo.type.DictTypeUpdateReqVO;
import cn.hmg.zackblog.module.system.convert.dict.DictTypeConvert;
import cn.hmg.zackblog.module.system.entity.dict.DictType;
import cn.hmg.zackblog.module.system.mapper.dict.DictTypeMapper;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

import static cn.hmg.zackblog.module.system.enums.ErrorCodeEnum.*;

/**
 * <p>
 * 字典类型 服务实现类
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Service
public class DictTypeServiceImpl extends ServiceImpl<DictTypeMapper, DictType> implements IDictTypeService {
    @Resource
    private DictTypeMapper dictTypeMapper;

    @Resource
    private IDictDataService dictDataService;


    @Override
    public PageResult<DictType> getPage(DictTypePageReqVO dictTypePageReqVO) {
        return dictTypeMapper.getPage(dictTypePageReqVO);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createDictType(DictTypeCreateReqVO dictTypeCreateReqVO) {
        //校验字典类型
        verifyDictTypeForCreateOrUpdate(null, dictTypeCreateReqVO.getName(),
                dictTypeCreateReqVO.getType(), dictTypeCreateReqVO.getStatus());

        //插入db
        dictTypeMapper.insert(DictTypeConvert.INSTANCE.convert(dictTypeCreateReqVO));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateDictType(DictTypeUpdateReqVO dictTypeUpdateReqVO) {
        Long id = dictTypeUpdateReqVO.getId();
        //校验字典类型是否存在
        verifyDictTypeIsExists(id);

        //校验字典类型
        verifyDictTypeForCreateOrUpdate(id, dictTypeUpdateReqVO.getName(),
                dictTypeUpdateReqVO.getType(), dictTypeUpdateReqVO.getStatus());

        //更新db
        dictTypeMapper.updateById(DictTypeConvert.INSTANCE.convert(dictTypeUpdateReqVO));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteDictTypeById(Long id) {
        //校验字典类型是否存在
        DictType dictType = verifyDictTypeIsExists(id);

        //校验是否有字典数据
        verifyIsExistsDictData(dictType.getType());

        //删除字典类型
        dictTypeMapper.deleteById(id);
    }

    @Override
    public DictType getDictTypeById(Long id) {
        return verifyDictTypeIsExists(id);
    }

    @Override
    public DictType selectByDictType(String type) {
        return dictTypeMapper.selectByDictType(type);
    }

    /**
     * 校验字典类型（此方法用于创建或更新操作）
     * @param id 字典类型id
     * @param name 字典类型名称
     * @param type 字典类型 类型
     * @param status 字典类型状态
     */
    private void verifyDictTypeForCreateOrUpdate(Long id, String name, String type, Integer status) {
        //校验部分字段是否唯一
        verifyDictTypeIsUnique(id, name, type);

        //校验状态
        verifyDictTypeStatus(status);
    }

    /**
     * 校验字典类型状态
     * @param status 字典类型状态
     */
    private void verifyDictTypeStatus(Integer status) {
        //校验状态
        Assert.isTrue(CommonStatusEnum.verifyStatusIsExists(status),
                () -> new BusinessException(DICT_TYPE_STATUS_ERROR.getCode(), DICT_TYPE_STATUS_ERROR.getMessage()));
    }

    /**
     * 校验字典数据是否存在
     * @param type 字典类型 类型
     */
    private void verifyIsExistsDictData(String type) {
        if (dictDataService.selectCountDictDataByDictType(type) > 0) {
            throw new BusinessException(DICT_TYPE_EXISTS_DICT_DATA.getCode(), DICT_TYPE_EXISTS_DICT_DATA.getMessage());
        }
    }

    /**
     * 校验字典类型是否存在
     * @param id 字典类型id
     * @return 字典类型
     */
    private DictType verifyDictTypeIsExists(Long id) {
        DictType dictType = dictTypeMapper.selectById(id);
        if (Objects.isNull(dictType)) {
            throw new BusinessException(DICT_TYPE_NOT_EXISTS.getCode(), DICT_TYPE_NOT_EXISTS.getMessage());
        }
        return dictType;
    }

    /**
     * 校验字典类型部分字段的唯一性
     * @param id 字典类型id
     * @param name 字典类型名称
     * @param type 字典类型 类型
     */
    private void verifyDictTypeIsUnique(Long id, String name, String type) {
        verifyNameIsUnique(id, name);
        verifyTypeIsUnique(id, type);
    }

    /**
     * 校验类型是否唯一
     * @param id 字典类型id
     * @param type 类型
     */
    private void verifyTypeIsUnique(Long id, String type) {
        DictType dictType = dictTypeMapper.selectByDictType(type);
        if (Objects.isNull(dictType)) {
            return;
        }

        Assert.notNull(id, () -> new BusinessException(DICT_TYPE_TYPE_ALREADY_EXISTS.getCode(),
                DICT_TYPE_TYPE_ALREADY_EXISTS.getMessage()));

        Assert.isTrue(ObjUtil.equals(id, dictType.getId()),
                () -> new BusinessException(DICT_TYPE_TYPE_ALREADY_EXISTS.getCode(),
                        DICT_TYPE_TYPE_ALREADY_EXISTS.getMessage()));
    }

    /**
     * 校验字典类型名称是否唯一
     * @param id 字典类型id
     * @param name 字典类型名称
     */
    private void verifyNameIsUnique(Long id, String name) {
        DictType dictType = dictTypeMapper.selectByDictName(name);
        if (Objects.isNull(dictType)) {
            return;
        }

        //断言，id不为空
        Assert.notNull(id, () -> new BusinessException(DICT_TYPE_NAME_ALREADY_EXISTS.getCode(),
                DICT_TYPE_NAME_ALREADY_EXISTS.getMessage()));

        //断言，传进来的id与查到的id一致，否则表示字典类型名称已存在
        Assert.isTrue(ObjUtil.equals(id, dictType.getId()),
                () -> new BusinessException(DICT_TYPE_NAME_ALREADY_EXISTS.getCode(),
                        DICT_TYPE_NAME_ALREADY_EXISTS.getMessage()));
    }
}
