package org.linn.validate.code.impl;

import org.apache.commons.lang3.StringUtils;
import org.linn.validate.code.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractValidateCodeProcessor<T extends ValidateCode> implements ValidateCodeProcessor {

    /**
     * 系统启动时收集 {@link ValidateCodeGenerator} 接口的实现
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;
    @Autowired
    private HttpSession httpSession;

    @Override
    public void create(ServletWebRequest request) throws Exception {
        T validateCode = generate(request);
        save(request, validateCode);
        send(request, validateCode);
    }

    /**
     * 保存验证码
     */
    private void save(ServletWebRequest request, T validateCode) {
        httpSession.setAttribute(this.getSessionKey(request), validateCode);
    }

    /**
     * 发送验证码,由字类实现
     */
    protected abstract void send(ServletWebRequest request, T validateCode) throws Exception;

    /**
     * 生成验证码
     */
    @SuppressWarnings("unchecked")
    private T generate(ServletWebRequest request) {
        String type = getValidateCodeType(request).toString().toLowerCase();
        String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
        //根据请求类型调用具体的验证码实现
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
        Optional.ofNullable(validateCodeGenerator).orElseThrow(() ->
                new ValidateCodeException("验证码生成器" + generatorName + "不存在"));
        return (T) validateCodeGenerator.generate(request);
    }

    /**
     * 根据请求的url 获取请求校验码的类型
     *
     * @param request {@link ServletWebRequest}
     * @return 返回请求验证码的类型 {@link ValidateCodeType} image | sms
     */
    private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }

    /**
     * 构建验证码放入session时的key
     *
     * @param request {@link ServletWebRequest}
     * @return
     */
    private String getSessionKey(ServletWebRequest request) {
        return SESSION_KEY_PREFIX + getValidateCodeType(request).toString().toUpperCase();
    }

    /**
     * 验证码校验
     *
     * @param request {@link ServletWebRequest }这是Spring提供的一个工具类，包装着request 和 response
     * @throws ServletRequestBindingException
     */
    @Override
    public void validate(ServletWebRequest request) throws ServletRequestBindingException {
        //枚举类中定义抽象方法，
        ValidateCodeType processorType = getValidateCodeType(request);
        System.out.println(ValidateCodeProcessor.SESSION_KEY_PREFIX + processorType);
        // ===========================>>
        String key = this.getSessionKey(request);
        T codeInSession = (T) httpSession.getAttribute(key);
        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), processorType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取短信验证码失败！");
        }
        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码不能为空");
        }
        if (codeInSession == null) {
            throw new ValidateCodeException("验证码不存在");
        }
        if (codeInSession.isExpire()) {
            httpSession.removeAttribute(key);
            throw new ValidateCodeException("验证码已过期");
        }
        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }
        httpSession.removeAttribute(key);
    }


}
