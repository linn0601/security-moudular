package org.linn.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码处理接口
 */
public interface ValidateCodeProcessor {

    /**
     * 验证码放入session是前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    /**
     * 创建验证码
     *
     * @param request {@link ServletWebRequest }这是Spring提供的一个工具类，包装着request 和 response
     * @throws Exception 异常处理
     */
    void create(ServletWebRequest request) throws Exception;

    /**
     * (noon-Javadoc)
     *
     * @see ValidateCodeProcessor#create(org.springframework.web.context.request.ServletWebRequest)
     */
    void validate(ServletWebRequest request) throws Exception;

}
