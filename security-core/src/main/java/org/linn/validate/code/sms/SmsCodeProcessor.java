package org.linn.validate.code.sms;

import lombok.RequiredArgsConstructor;
import org.linn.validate.code.ValidateCode;
import org.linn.validate.code.impl.AbstractValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 发送逻辑的具体调用
 */
@Component("smsValidateCodeProcessor")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

    private final SmsCodeSender smsCodeSender;

    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile");
        smsCodeSender.send(mobile, validateCode.getCode());
    }
}