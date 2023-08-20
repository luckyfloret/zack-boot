package cn.hmg.zackblog.module.system.service.dict;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.system.controller.admin.dict.vo.type.DictTypeCreateReqVO;
import cn.hmg.zackblog.module.system.controller.admin.dict.vo.type.DictTypePageReqVO;
import cn.hmg.zackblog.module.system.controller.admin.dict.vo.type.DictTypeUpdateReqVO;
import cn.hmg.zackblog.module.system.entity.dict.DictType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 字典类型 服务类
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
public interface IDictTypeService extends IService<DictType> {

    /**
     * 字典类型分页
     *
     * @param dictTypePageReqVO 字典类型分页 request vo
     * @return 字典类型分页
     */
    PageResult<DictType> getPage(DictTypePageReqVO dictTypePageReqVO);

    /**
     * 创建字典类型
     *
     * @param dictTypeCreateReqVO 创建字典类型 request vo
     */
    void createDictType(DictTypeCreateReqVO dictTypeCreateReqVO);

    /**
     * 更新字典类型
     *
     * @param dictTypeUpdateReqVO 更新字典类型 request vo
     */
    void updateDictType(DictTypeUpdateReqVO dictTypeUpdateReqVO);

    /**
     * 根据id删除字典类型
     *
     * @param id 字典类型id
     */
    void deleteDictTypeById(Long id);

    /**
     * 根据id获取字典类型
     *
     * @param id 字典类型id
     * @return 字典类型
     */
    DictType getDictTypeById(Long id);

    /**
     * 根据类型查询字典类型
     *
     * @param type 类型
     * @return 字典类型
     */
    DictType selectByDictType(String type);

    List<DictType> getDictTypeList();
}
