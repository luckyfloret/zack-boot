package cn.hmg.zackblog.module.website.mapper;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.mybatisplus.core.mapper.BaseMapperExtend;
import cn.hmg.zackblog.framework.mybatisplus.core.query.LambdaQueryWrapperExtend;
import cn.hmg.zackblog.module.website.controller.admin.vo.issues.IssuesPageReqVO;
import cn.hmg.zackblog.module.website.entity.Issues;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 问题管理表 Mapper 接口
 * </p>
 *
 * @author hmg
 * @since 2023-08-30
 */
@Mapper
public interface IssuesMapper extends BaseMapperExtend<Issues> {

    default PageResult<Issues> getPage(IssuesPageReqVO reqVO) {
        return page(reqVO, new LambdaQueryWrapperExtend<Issues>()
                .likeIfExists(Issues::getTitle, reqVO.getTitle())
                .eqIfExists(Issues::getType, reqVO.getType())
                .eqIfExists(Issues::getStatus, reqVO.getStatus())
        );
    }
}
