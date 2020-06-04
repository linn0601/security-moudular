package org.linn.validate.code.impl;

import org.linn.validate.code.ValidateCode;
import org.linn.validate.code.ValidateCodeRepository;
import org.linn.validate.code.ValidateCodeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpSession;

@Component
public class SessionValidateCodeRepository implements ValidateCodeRepository {

    /**
     * 验证码放入session是前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    @Autowired
    private HttpSession httpSession;

    /**
     * (no-Javadoc)
     * org.linn.validate.code.ValidateCodeRepository#save
     */
    @Override
    public void save(ServletWebRequest request, ValidateCode validateCode, ValidateCodeType type) {
        ValidateCode code = new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
        String key = this.getSessionKey(request, type);
        httpSession.setAttribute(key, code);
    }

    /**
     * (no-Javadoc)
     *
     * @see ValidateCodeRepository#get
     */
    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType type) {
        String key = this.getSessionKey(request, type);
        return (ValidateCode) httpSession.getAttribute(key);
    }

    /**
     * (no-Javadoc)
     *
     * @see ValidateCodeRepository#remove
     */
    @Override
    public void remove(ServletWebRequest request, ValidateCodeType type) {
        String key = this.getSessionKey(request, type);
        httpSession.removeAttribute(key);
    }

    /**
     * 构建验证码放入session时的key
     *
     * @param request {@link ServletWebRequest}
     */
    private String getSessionKey(ServletWebRequest request, ValidateCodeType validateCodeType) {
        return SESSION_KEY_PREFIX + validateCodeType.toString().toUpperCase();
    }
}
