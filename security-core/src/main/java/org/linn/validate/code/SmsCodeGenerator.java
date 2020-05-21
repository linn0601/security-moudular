package org.linn.validate.code;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.linn.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Setter
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SmsCodeGenerator implements ValidateCodeGenerator {

    private final SecurityProperties securityProperties;

    @Override
    public ValidateCode generate() {
        String smsCode = RandomStringUtils.randomNumeric(securityProperties.getCode().getSmsCode().getLength());
        return new ValidateCode(smsCode, securityProperties.getCode().getImage().getExpireIn());
    }
}
