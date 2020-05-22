package org.linn.validate.code;

import org.linn.constants.SecurityConstants;

public enum ValidateCodeType {

    /**
     * 图形验证码
     */
    IMAGE {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
        }
    },
    /**
     * 手机验证码
     */
    SMS {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
        }
    };

    /**
     * 校验从请求中获取参数的名字
     */
    public abstract String getParamNameOnValidate();
}
