package cn.hmg.zackblog.module.system.controller.admin.captcha;

import cn.hmg.zackblog.common.pojo.CommonResult;
import cn.hutool.extra.servlet.ServletUtil;
import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.anji.captcha.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-13 12:01
 * @description: 验证码控制器
 */
@RestController
@RequestMapping("/admin/captcha")
@Slf4j
public class CaptchaController {
    @Resource
    private CaptchaService captchaService;

    @PostMapping("/get")
    public ResponseModel get(@RequestBody CaptchaVO captchaVO, HttpServletRequest request){
        assert request.getRemoteHost() != null;
        captchaVO.setBrowserInfo(getRemoteId(request));
        return captchaService.get(captchaVO);
    }

    @PostMapping("/check")
    public ResponseModel check(@RequestBody CaptchaVO captchaVO, HttpServletRequest request) {
        log.info("captcha check start....");
        captchaVO.setBrowserInfo(getRemoteId(request));
        return captchaService.check(captchaVO);
    }

    public static String getRemoteId(HttpServletRequest request) {
        String ip = ServletUtil.getClientIP(request);
        String ua = request.getHeader("user-agent");
        if (StringUtils.isNotBlank(ip)) {
            return ip + ua;
        }
        return request.getRemoteAddr() + ua;
    }

}
