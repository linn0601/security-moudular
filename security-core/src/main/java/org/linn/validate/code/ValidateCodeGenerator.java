package org.linn.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeGenerator {

    /**
     * 生成验证码接口
     * @return {@link ValidateCode}
     */
    ValidateCode generate(ServletWebRequest request);
}
