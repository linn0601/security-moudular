package org.linn.validate.code.image;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.linn.properties.SecurityProperties;
import org.linn.validate.code.ValidateCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

@Setter
@Component("imageValidateCodeGenerator")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImageCodeGenerator implements ValidateCodeGenerator {

    private final SecurityProperties securityProperties;

    /**
     * 图形验证码的生辰
     * @return {@link ImageCode } 验证码的封装
     */
    @Override
    public ImageCode generate(ServletWebRequest request) {
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(
                securityProperties.getCode().getImage().getWidth(),
                securityProperties.getCode().getImage().getHeight(),
                securityProperties.getCode().getImage().getLength(),
                securityProperties.getCode().getImage().getCircleCount());
        return new ImageCode(circleCaptcha.getImage(), circleCaptcha.getCode(),
                securityProperties.getCode().getImage().getExpireIn());
    }
}
