package org.linn.validate.code.impl;

import org.apache.commons.lang3.StringUtils;
import org.linn.validate.code.ValidateCodeGenerator;
import org.linn.validate.code.ValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpSession;
import java.util.Map;

public abstract class AbstractValidateCodeProcessor<T> implements ValidateCodeProcessor {

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
        httpSession.setAttribute(ValidateCodeProcessor.SESSION_KEY_PREFIX + getProcessorType(request).toUpperCase(),
                validateCode);
    }

    /**
     * 发送验证码
     */
    protected abstract void send(ServletWebRequest request, T validateCode) throws Exception;

    /**
     * 生成验证码
     */
    @SuppressWarnings("unchecked")
    private T generate(ServletWebRequest request) {
        String type = getProcessorType(request);
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(type + "CodeGenerator");
        return (T) validateCodeGenerator.generate();
    }

    /**
     * 根据请求的url 获取请求校验码的类型
     *
     * @param request {@link ServletWebRequest}
     * @return 返回请求验证码的类型 image | sms
     */
    private String getProcessorType(ServletWebRequest request) {
        return StringUtils.substringAfter(request.getRequest().getRequestURI(), "/code/");
    }
}
