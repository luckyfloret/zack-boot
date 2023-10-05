package cn.hmg.zackblog.module.website.mapper.issues;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.mybatisplus.core.mapper.BaseMapperExtend;
import cn.hmg.zackblog.framework.mybatisplus.core.query.LambdaQueryWrapperExtend;
import cn.hmg.zackblog.module.website.controller.admin.issues.vo.IssuesPageReqVO;
import cn.hmg.zackblog.module.website.entity.issues.Issues;
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
