package cn.hmg.zackblog.module.website.service.issues;

import cn.hmg.zackblog.framework.common.exception.BusinessException;
import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.website.controller.admin.issues.vo.IssuesPageReqVO;
import cn.hmg.zackblog.module.website.entity.issues.Issues;
import cn.hmg.zackblog.module.website.enums.IssuesStatusEnum;
import cn.hmg.zackblog.module.website.mapper.issues.IssuesMapper;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

import static cn.hmg.zackblog.module.website.enums.ErrorCodeEnum.ISSUES_NOT_EXISTS;
import static cn.hmg.zackblog.module.website.enums.ErrorCodeEnum.ISSUES_STATUS_ALREADY_UPDATE;

/**
 * <p>
 * 问题管理表 服务实现类
 * </p>
 *
 * @author hmg
 * @since 2023-08-30
 */
@Service
public class IssuesServiceImpl extends ServiceImpl<IssuesMapper, Issues> implements IIssuesService {

    @Resource
    private IssuesMapper issuesMapper;

    @Override
    public PageResult<Issues> getPage(IssuesPageReqVO reqVO) {
        return issuesMapper.getPage(reqVO);
    }

    @Override
    public Issues getIssuesById(Long id) {
        return verifyIssuesIsExists(id);
    }

    @Override
    public void updateStatus(Long id) {
        //校验issues 是否存在
        Issues issues = verifyIssuesIsExists(id);
        //校验issues 状态
        Assert.isTrue(IssuesStatusEnum.TO_BE_SOLVED.getStatus().equals(issues.getStatus()),
                () -> new BusinessException(ISSUES_STATUS_ALREADY_UPDATE.getCode(), ISSUES_STATUS_ALREADY_UPDATE.getMessage()));

        //更新
        issuesMapper.updateById(issues.setStatus(IssuesStatusEnum.ALREADY_SOLVED.getStatus()));

        //TODO 获取用户信息并发送邮箱
    }

    /**
     * 校验 issues 是否存在
     * @param issuesId issues id
     * @return issues
     */
    private Issues verifyIssuesIsExists(Long issuesId) {
        Issues issues = issuesMapper.selectById(issuesId);
        if (Objects.isNull(issues)) {
            throw new BusinessException(ISSUES_NOT_EXISTS.getCode(), ISSUES_NOT_EXISTS.getMessage());
        }

        return issues;
    }
}
