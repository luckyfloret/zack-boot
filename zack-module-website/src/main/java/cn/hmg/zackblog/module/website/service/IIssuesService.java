package cn.hmg.zackblog.module.website.service;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.website.controller.admin.vo.issues.IssuesPageReqVO;
import cn.hmg.zackblog.module.website.entity.Issues;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 问题管理表 服务类
 * </p>
 *
 * @author hmg
 * @since 2023-08-30
 */
public interface IIssuesService extends IService<Issues> {

    PageResult<Issues> getPage(IssuesPageReqVO reqVO);

    Issues getIssuesById(Long id);

    void updateStatus(Long id);
}
