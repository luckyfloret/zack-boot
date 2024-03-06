package cn.hmg.zackblog.module.system.service.dict;

import cn.hmg.zackblog.framework.common.enums.CommonStatusEnum;
import cn.hmg.zackblog.framework.common.exception.BusinessException;
import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.system.controller.admin.dict.vo.data.DictDataCreateReqVO;
import cn.hmg.zackblog.module.system.controller.admin.dict.vo.data.DictDataPageReqVO;
import cn.hmg.zackblog.module.system.controller.admin.dict.vo.data.DictDataUpdateReqVO;
import cn.hmg.zackblog.module.system.convert.dict.DictDataConvert;
import cn.hmg.zackblog.module.system.entity.dict.DictData;
import cn.hmg.zackblog.module.system.entity.dict.DictType;
import cn.hmg.zackblog.module.system.mapper.dict.DictDataMapper;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

import static cn.hmg.zackblog.module.system.enums.ErrorCodeEnum.*;

/**
 * <p>
 * 字典数据 服务实现类
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Service
public class DictDataServiceImpl extends ServiceImpl<DictDataMapper, DictData> implements IDictDataService {

    @Resource
    private DictDataMapper dictDataMapper;

    @Resource
    private IDictTypeService dictTypeService;


    @Override
    public Long selectCountDictDataByDictType(String type) {
        return dictDataMapper.selectCountByDictType(type);
    }

    @Override
    public PageResult<DictData> getPage(DictDataPageReqVO dictDataPageReqVO) {
        return dictDataMapper.getPage(dictDataPageReqVO);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createDictData(DictDataCreateReqVO dictDataCreateReqVO) {
        //校验字典类型、字典数据
        verifyDictDataForCreateOrUpdate(null, dictDataCreateReqVO.getDictType(),
                dictDataCreateReqVO.getValue(), dictDataCreateReqVO.getStatus());

        //加入db
        dictDataMapper.insert(DictDataConvert.INSTANCE.convert(dictDataCreateReqVO));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateDictData(DictDataUpdateReqVO dictDataUpdateReqVO) {
        //校验字典数据是否存在
        verifyDictDataIsExists(dictDataUpdateReqVO.getId());

        //校验字典类型、字典数据
        verifyDictDataForCreateOrUpdate(dictDataUpdateReqVO.getId(), dictDataUpdateReqVO.getDictType(),
                dictDataUpdateReqVO.getValue(), dictDataUpdateReqVO.getStatus());

        //更新db
        dictDataMapper.updateById(DictDataConvert.INSTANCE.convert(dictDataUpdateReqVO));
    }

    @Override
    public DictData getDictDataById(Long id) {
        return verifyDictDataIsExists(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteDictDataById(Long id) {
        //校验字典数据是否存在
        verifyDictDataIsExists(id);

        //删除数据字典
        dictDataMapper.deleteById(id);
    }

    /**
     * 校验字典数据（用于创建或更新操作）
     *
     * @param id       字典数据id
     * @param dictType 字典类型 类型
     * @param value    字典数据键值
     * @param status   字典数据状态
     */
    private void verifyDictDataForCreateOrUpdate(Long id, String dictType, String value, Integer status) {
        //校验字典类型有效性
        verifyDictType(dictType);

        //校验部分字段唯一性
        verifyFieldIsUnique(id, dictType, value);

        //校验状态
        verifyDictDataStatus(status);
    }

    /**
     * 校验字典数据是否存在
     *
     * @param id 字典数据id
     * @return 字典数据
     */
    private DictData verifyDictDataIsExists(Long id) {
        DictData dictData = dictDataMapper.selectById(id);
        Assert.notNull(dictData, () -> new BusinessException(DICT_DATA_NOT_EXISTS.getCode(),
                DICT_DATA_NOT_EXISTS.getMessage()));
        return dictData;
    }

    /**
     * 校验字典类型
     *
     * @param type 字典类型 类型
     */
    private void verifyDictType(String type) {
        DictType dictType = dictTypeService.selectByDictType(type);

        //校验字典类型是否存在
        Assert.notNull(dictType, () -> new BusinessException(DICT_TYPE_NOT_EXISTS.getCode(),
                DICT_TYPE_NOT_EXISTS.getMessage()));

        //状态必须是启用，不然字典数据不能选择该字典类型
        Assert.isTrue(CommonStatusEnum.ENABLED.getStatusCode().equals(dictType.getStatus()),
                () -> new BusinessException(DICT_TYPE_NOT_ENABLE.getCode(), DICT_TYPE_NOT_ENABLE.getMessage()));
    }

    /**
     * 校验字典数据状态
     *
     * @param status 字典数据状态
     */
    private void verifyDictDataStatus(Integer status) {
        //校验字典数据状态是否合法
        Assert.isTrue(CommonStatusEnum.verifyStatusIsExists(status),
                () -> new BusinessException(DICT_DATA_STATUS_ERROR.getCode(), DICT_DATA_STATUS_ERROR.getMessage()));

    }

    /**
     * 校验部分字段唯一性
     *
     * @param id       字典数据id
     * @param dictType 字典类型 类型
     * @param value    字典数据键值
     */
    private void verifyFieldIsUnique(Long id, String dictType, String value) {
        DictData dictData = dictDataMapper.selectOne(dictType, value);

        if (Objects.isNull(dictData)) {
            return;
        }

        //断言id不为空，如果id为空说明是创建操作，并且字典数据存在
        Assert.notNull(id, () -> new BusinessException(DICT_DATA_VALUE_ALREADY_EXISTS.getCode(),
                DICT_DATA_VALUE_ALREADY_EXISTS.getMessage()));

        //断言传进来的字典数据id是否与查询的id一致， 不一致说明字典数据存在
        Assert.isTrue(dictData.getId().equals(id),
                () -> new BusinessException(DICT_DATA_VALUE_ALREADY_EXISTS.getCode(),
                        DICT_DATA_VALUE_ALREADY_EXISTS.getMessage()));
    }

}
