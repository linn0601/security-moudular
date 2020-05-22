package org.linn.validate.code;

import lombok.RequiredArgsConstructor;
import org.linn.properties.SecurityProperties;
import org.linn.validate.code.image.ImageCodeGenerator;
import org.linn.validate.code.sms.DefaultSmsCodeSender;
import org.linn.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 一个关于短信和图形验证码Bean的配置，提供重写逻辑途径
 * {@link ValidateCodeGenerator} 关于图形验证码的实现
 * {@link SmsCodeSender} 关于短信验证码的实现
 */
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ValidateCodeBeanConfig {

    private final SecurityProperties securityProperties;

    /**
     * 容器启动时查找是否有imageCodeGenerator的Bean，如果有就不会使用下面的Bean
     */
    @Bean
    @ConditionalOnMissingBean(ValidateCodeGenerator.class)
    public ValidateCodeGenerator imageCodeGenerator(){
        return new ImageCodeGenerator(securityProperties);
    }

    /**
     * 发送验证码供应商
     * 容器启动时查找是否有smsCodeSender的Bean，如果有就不会使用下面的Bean
     */
    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender(){
        return new DefaultSmsCodeSender();
    }
}
