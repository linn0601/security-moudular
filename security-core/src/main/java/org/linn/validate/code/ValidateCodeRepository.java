package org.linn.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * create by linn 2020/06/04
 */
public interface ValidateCodeRepository {

    /**
     * 保存验证码
     *
     * @param request 封装了request 和 response
     * @param code    验证码对象
     * @param type    验证码类型
     */
    void save(ServletWebRequest request, ValidateCode code, ValidateCodeType type);

    /**
     * 获取验证码
     *
     * @param request 封装了request 和 response
     * @param type    验证码类型
     */
    ValidateCode get(ServletWebRequest request, ValidateCodeType type);

    /**
     * 移除验证码
     *
     * @param request 封装了request 和 response
     * @param type    验证码类型
     */
    void remove(ServletWebRequest request, ValidateCodeType type);
}
