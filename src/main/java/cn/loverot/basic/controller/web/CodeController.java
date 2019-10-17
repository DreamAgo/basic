package cn.loverot.basic.controller.web;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.loverot.basic.controller.BaseController;
import cn.loverot.basic.constant.e.SessionConstEnum;
import cn.loverot.basic.utils.BasicUtil;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 铭飞开源团队--huise
 * @Date: 2019/10/17 0:59
 */
@RestController
@RequestMapping
public class CodeController extends BaseController {

    @RequestMapping(value = "/code",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] code(HttpServletResponse response) throws IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        //验证码宽度
        int imgWidth=BasicUtil.getInt("imgWidth",100),
//                验证码高度
                imgHeight=BasicUtil.getInt("imgHeight",50),
//                混淆字符数
                obsSize=BasicUtil.getInt("obsSize",50),
//                字符个数
                imgSize=BasicUtil.getInt("imgSize",4);
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(imgWidth,imgHeight,imgSize,obsSize );
        BasicUtil.setSession(SessionConstEnum.CODE_SESSION.toString(),lineCaptcha.getCode());
        LOG.debug("randCode:{}",lineCaptcha.getCode());
        return lineCaptcha.getImageBytes();
    }
}
