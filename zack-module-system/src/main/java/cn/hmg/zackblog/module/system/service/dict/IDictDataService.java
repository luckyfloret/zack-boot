package cn.hmg.zackblog.module.system.service.dict;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.system.controller.admin.dict.vo.data.DictDataCreateReqVO;
import cn.hmg.zackblog.module.system.controller.admin.dict.vo.data.DictDataPageReqVO;
import cn.hmg.zackblog.module.system.controller.admin.dict.vo.data.DictDataUpdateReqVO;
import cn.hmg.zackblog.module.system.entity.dict.DictData;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 数据字典 服务类
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
public interface IDictDataService extends IService<DictData> {

    /**
     * 根据字典类型 类型统计字典数据数量
     *
     * @param type 字典类型的类型
     * @return 统计的数量
     */
    Long selectCountDictDataByDictType(String type);

    /**
     * 字典数据分页
     *
     * @param dictDataPageReqVO 字典数据分页 request vo
     * @return 字典数据分页
     */
    PageResult<DictData> getPage(DictDataPageReqVO dictDataPageReqVO);

    /**
     * 创建字典数据
     *
     * @param dictDataCreateReqVO 创建字典数据 request vo
     */
    void createDictData(DictDataCreateReqVO dictDataCreateReqVO);

    /**
     * 更新字典数据
     *
     * @param dictDataUpdateReqVO 更新字典数据 request vo
     */
    void updateDictData(DictDataUpdateReqVO dictDataUpdateReqVO);

    /**
     * 根据id获取字典数据
     *
     * @param id 字典数据id
     * @return 字典数据
     */
    DictData getDictDataById(Long id);

    /**
     * 根据id删除字典数据
     *
     * @param id 字典数据id
     */
    void deleteDictDataById(Long id);
}
