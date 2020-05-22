package org.linn.validate.code.sms;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.linn.properties.SecurityProperties;
import org.linn.validate.code.ValidateCode;
import org.linn.validate.code.ValidateCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

@Setter
@Component("smsValidateCodeGenerator")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SmsCodeGenerator implements ValidateCodeGenerator {

    private final SecurityProperties securityProperties;

    /**
     * 生成短信验证码具体实现
     * @param request {@link ServletWebRequest}
     */
    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String smsCode = RandomStringUtils.randomNumeric(securityProperties.getCode().getSmsCode().getLength());
        return new ValidateCode(smsCode, securityProperties.getCode().getSmsCode().getExpireIn());
    }
}
