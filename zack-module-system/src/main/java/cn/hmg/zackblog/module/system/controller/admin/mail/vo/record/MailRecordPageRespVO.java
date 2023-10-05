package cn.hmg.zackblog.module.system.controller.admin.mail.vo.record;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-22 19:14
 * @description:
 */
@Data
public class MailRecordPageRespVO extends BaseMailRecordVO{
    @Schema(description = "id （主键）")
    private Long id;
}
