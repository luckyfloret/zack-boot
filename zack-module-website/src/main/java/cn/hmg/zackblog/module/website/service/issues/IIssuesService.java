package cn.hmg.zackblog.module.website.service.issues;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.website.controller.admin.issues.vo.IssuesPageReqVO;
import cn.hmg.zackblog.module.website.entity.issues.Issues;
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
    /**
     * 获取issues分页列表
     * @param reqVO 请求参数
     * @return issues分页列表
     */
    PageResult<Issues> getPage(IssuesPageReqVO reqVO);

    /**
     * 根据id获取issues
     * @param id issuesId
     * @return issues
     */
    Issues getIssuesById(Long id);

    /**
     * 更新issues状态
     * @param id issuesId
     */
    void updateStatus(Long id);
}
