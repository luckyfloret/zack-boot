package cn.hmg.zackblog.module.website.convert.admin.issues;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.website.controller.admin.vo.issues.IssuesPageRespVO;
import cn.hmg.zackblog.module.website.controller.admin.vo.issues.IssuesRespVO;
import cn.hmg.zackblog.module.website.entity.Issues;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-03 11:14
 * @description:
 */
@Mapper
public interface IssuesConvert {
    IssuesConvert INSTANCE = Mappers.getMapper(IssuesConvert.class);

    PageResult<IssuesPageRespVO> convert(PageResult<Issues> pageResult);

    IssuesRespVO convert(Issues issues);
}
