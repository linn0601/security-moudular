package org.linn.validate.code.sms;

/**
 * 发送短信验证码默认实现
 */
public class DefaultSmsCodeSender implements SmsCodeSender {

    @Override
    public void send(String mobile, String code) {
        System.out.println("向手机号  " + mobile + " 发送验证码: " + code);
    }
}
