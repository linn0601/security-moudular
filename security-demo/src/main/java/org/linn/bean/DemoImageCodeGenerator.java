package org.linn.bean;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import org.linn.validate.code.image.ImageCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 重写自己的验证码实现类
 */
//@Component
public class DemoImageCodeGenerator /*implements ValidateCodeGenerator*/ {

    //@Override
    public ImageCode generate(ServletWebRequest request) {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        return new ImageCode(lineCaptcha.getImage(),lineCaptcha.getCode(),60);
    }

}
