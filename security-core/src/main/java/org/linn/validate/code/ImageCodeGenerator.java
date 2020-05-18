package org.linn.validate.code;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import lombok.RequiredArgsConstructor;
import org.linn.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImageCodeGenerator implements ValidateCodeGenerator {

    private final SecurityProperties securityProperties;

    @Override
    public ImageCode createImageCode() {
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(
                securityProperties.getCode().getImage().getWidth(),
                securityProperties.getCode().getImage().getHeight(),
                securityProperties.getCode().getImage().getLength(),
                securityProperties.getCode().getImage().getCircleCount());
        return new ImageCode(circleCaptcha.getImage(), circleCaptcha.getCode(),
                securityProperties.getCode().getImage().getExpireIn());
    }
}
