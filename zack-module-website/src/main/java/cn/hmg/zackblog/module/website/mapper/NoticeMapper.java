package cn.hmg.zackblog.module.website.mapper;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.mybatisplus.core.mapper.BaseMapperExtend;
import cn.hmg.zackblog.framework.mybatisplus.core.query.LambdaQueryWrapperExtend;
import cn.hmg.zackblog.module.website.controller.admin.vo.notice.NoticePageReqVO;
import cn.hmg.zackblog.module.website.entity.Notice;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 消息管理 Mapper 接口
 * </p>
 *
 * @author hmg
 * @since 2023-08-30
 */
@Mapper
public interface NoticeMapper extends BaseMapperExtend<Notice> {

    default PageResult<Notice> getPage(NoticePageReqVO reqVO) {
        return page(reqVO, new LambdaQueryWrapperExtend<Notice>()
                .eqIfExists(Notice::getType, reqVO.getType())
                .likeIfExists(Notice::getTitle, reqVO.getTitle())
        );
    }

    default void updateBatch(List<Notice> noticeList) {
        updateBatchById(noticeList);
    }

    default List<Notice> selectListByType(Integer type) {
        return selectList(new LambdaQueryWrapperExtend<Notice>().eq(Notice::getType, type));
    }
}
