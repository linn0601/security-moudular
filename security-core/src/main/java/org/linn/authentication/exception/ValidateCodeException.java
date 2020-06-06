package org.linn.authentication.exception;

import org.springframework.security.core.AuthenticationException;

public class ValidateCodeException extends AuthenticationException {

    /**
     * 认证过程中的异常信息，最终交给失败处理器去处理
     * @see org.springframework.security.web.access.ExceptionTranslationFilter
     * @param msg 异常信息
     */
    public ValidateCodeException(String msg) {
        super(msg);
    }
}
